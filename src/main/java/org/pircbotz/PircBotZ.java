package org.pircbotz;
/*
Fork of: https://github.com/AE97/PircBotY (7/28/14) which is Fork of https://code.google.com/p/pircbotx/ (Unknown Date) which in turn is Fork of PircBot
 */
import com.google.common.collect.ImmutableMap;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.pircbotz.hooks.events.DisconnectEvent;
import org.pircbotz.hooks.events.SocketConnectEvent;
import org.pircbotz.dcc.DccHandler;
import org.pircbotz.exception.IrcException;
import org.pircbotz.output.OutputCAP;
import org.pircbotz.output.OutputDCC;
import org.pircbotz.output.OutputIRC;
import org.pircbotz.output.OutputRaw;
import org.pircbotz.snapshot.ChannelSnapshot;
import org.pircbotz.snapshot.UserSnapshot;
import org.apache.commons.lang3.StringUtils;

public class PircBotZ implements Comparable<PircBotZ> {

    public static final String VERSION = "1.1.0";
    private static final AtomicInteger BOT_COUNT = new AtomicInteger();
    private final int botId;
    private final Configuration<PircBotZ> configuration;
    private final List<String> enabledCapabilities = new LinkedList<>();
    private InputParser inputParser;
    private UserChannelDao<PircBotZ, User, Channel> userChannelDao;
    private DccHandler dccHandler;
    private ServerInfo serverInfo;
    private Socket socket;
    private BufferedReader inputReader;
    private OutputStreamWriter outputWriter;
    private OutputRaw outputRaw;
    private OutputIRC outputIRC;
    private OutputCAP outputCAP;
    private OutputDCC outputDCC;
    private String nick = "";
    private boolean loggedIn = false;
    private Thread shutdownHook;
    private boolean reconnectStopped = false;
    private ImmutableMap<String, String> reconnectChannels;
    private State state = State.INIT;
    private Exception disconnectException;
    private InputProcessor inputProcessor;


    public PircBotZ(Configuration<PircBotZ> configuration) {
        botId = BOT_COUNT.getAndIncrement();
        this.configuration = configuration;
    }

    public void startBot() throws IOException, IrcException {
        reconnectStopped = false;
        connect();
    }

    public void stopBotReconnect() {
        reconnectStopped = true;
    }

    protected void connect() throws IOException, IrcException {
        if (isConnected()) {
            throw new IrcException(IrcException.Reason.AlreadyConnected, "Must disconnect from server before connecting again");
        }
        if (getState() == State.CONNECTED) {
            throw new RuntimeException("Bot is not connected but state is State.CONNECTED. This shouldn't happen");
        }
        this.userChannelDao = configuration.getBotFactory().createUserChannelDao(this);
        this.serverInfo = configuration.getBotFactory().createServerInfo(this);
        this.outputRaw = configuration.getBotFactory().createOutputRaw(this);
        this.outputIRC = configuration.getBotFactory().createOutputIRC(this);
        this.outputCAP = configuration.getBotFactory().createOutputCAP(this);
        this.outputDCC = configuration.getBotFactory().createOutputDCC(this);
        this.dccHandler = configuration.getBotFactory().createDccHandler(this);
        this.inputParser = configuration.getBotFactory().createInputParser(this);
        enabledCapabilities.clear();
        for (InetAddress curAddress : InetAddress.getAllByName(configuration.getServerHostname())) {
            try {
                socket = configuration.getSocketFactory().createSocket(curAddress, configuration.getServerPort(), configuration.getLocalAddress(), 0);
                break;
            } catch (IOException e) {
            }
        }
        if (socket == null || (socket != null && !socket.isConnected())) {
            throw new IOException("Unable to connect to the IRC network " + configuration.getServerHostname());
        }
        state = State.CONNECTED;
        socket.setSoTimeout(configuration.getSocketTimeout());
        inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), configuration.getEncoding()));
        outputWriter = new OutputStreamWriter(socket.getOutputStream(), configuration.getEncoding());
        configuration.getListenerManager().dispatchEvent(new SocketConnectEvent(this));
        if (configuration.isCapEnabled()) {
            sendCAP().requestSupported();
        }
        if (configuration.isWebIrcEnabled()) {
            sendRaw().rawLineNow("WEBIRC " + configuration.getWebIrcPassword()
                    + " " + configuration.getWebIrcUsername()
                    + " " + configuration.getWebIrcHostname()
                    + " " + configuration.getWebIrcAddress().getHostAddress());
        }
        if (StringUtils.isNotBlank(configuration.getServerPassword())) {
            sendRaw().rawLineNow("PASS " + configuration.getServerPassword());
        }
        sendRaw().rawLineNow("NICK " + configuration.getName());
        sendRaw().rawLineNow("USER " + configuration.getLogin() + " 8 * :" + configuration.getRealName());
        inputProcessor = new InputProcessor();
        inputProcessor.start();
    }

    protected void sendRawLineToServer(String line) {
        if (line.length() > configuration.getMaxLineLength() - 2) {
            line = line.substring(0, configuration.getMaxLineLength() - 2);
        }
        try {
            outputWriter.write(line + "\r\n");
            outputWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException("Exception encountered when writing to socket", e);
        }
    }

    protected void loggedIn(String nick) {
        this.loggedIn = true;
        setNick(nick);
        if (configuration.isShutdownHookEnabled()) {
            Runtime.getRuntime().addShutdownHook(shutdownHook = new PircBotZ.BotShutdownHook(this));
        }
    }

    public OutputRaw sendRaw() {
        return outputRaw;
    }

    public OutputIRC sendIRC() {
        return outputIRC;
    }

    public OutputCAP sendCAP() {
        return outputCAP;
    }

    public OutputDCC sendDCC() {
        return outputDCC;
    }

    public int getBotId() {
        return botId;
    }

    public Configuration<PircBotZ> getConfiguration() {
        return configuration;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public UserChannelDao<PircBotZ, User, Channel> getUserChannelDao() {
        return userChannelDao;
    }

    public List<String> getEnabledCapabilities() {
        return enabledCapabilities;
    }

    public OutputRaw getOutputRaw() {
        return outputRaw;
    }

    public OutputIRC getOutputIRC() {
        return outputIRC;
    }

    public OutputCAP getOutputCAP() {
        return outputCAP;
    }

    public Thread getShutdownHook() {
        return shutdownHook;
    }

    public boolean isReconnectStopped() {
        return reconnectStopped;
    }

    public ImmutableMap<String, String> getReconnectChannels() {
        return reconnectChannels;
    }

    public Exception getDisconnectException() {
        return disconnectException;
    }

    public DccHandler getDccHandler() {
        return dccHandler;
    }

    public InputParser getInputParser() {
        return inputParser;
    }

    protected void setNick(String nick) {
        this.nick = nick;
    }

    public String getNick() {
        return nick;
    }

    public boolean isConnected() {
        return socket != null && !socket.isClosed();
    }

    @Override
    public String toString() {
        return "Version{" + configuration.getVersion() + "}"
                + " Connected{" + isConnected() + "}"
                + " Server{" + configuration.getServerHostname() + "}"
                + " Port{" + configuration.getServerPort() + "}"
                + " Password{" + configuration.getServerPassword() + "}";
    }

    public User getUserBot() {
        return userChannelDao.getUser(getNick());
    }

    public ServerInfo getServerInfo() {
        return serverInfo;
    }

    public InetAddress getLocalAddress() {
        return socket.getLocalAddress();
    }

    protected ImmutableMap<String, String> reconnectChannels() {
        ImmutableMap<String, String> reconnectChannelsLocal = reconnectChannels;
        reconnectChannels = null;
        return reconnectChannelsLocal;
    }

    public void shutdown() {
        shutdown(false);
    }

    public void shutdown(boolean noReconnect) {
        UserChannelDao<PircBotZ, UserSnapshot, ChannelSnapshot> daoSnapshot;
        if (state == State.DISCONNECTED) {
            throw new RuntimeException("Cannot call shutdown twice");
        }
        state = State.DISCONNECTED;
        try {
            socket.close();
        } catch (IOException e) {
        }
        if (socket != null && !socket.isClosed()) {
            try {
                socket.close();
            } catch (IOException e) {
            }
        }
        ImmutableMap.Builder<String, String> reconnectChannelsBuilder = new ImmutableMap.Builder<>();
        for (Channel curChannel : userChannelDao.getAllChannels()) {
            String key = (curChannel.getChannelKey() == null) ? "" : curChannel.getChannelKey();
            reconnectChannelsBuilder.put(curChannel.getName(), key);
        }
        reconnectChannels = reconnectChannelsBuilder.build();
        loggedIn = false;
        daoSnapshot = userChannelDao.createSnapshot();
        userChannelDao.close();
        inputParser.close();
        dccHandler.close();
        configuration.getListenerManager().dispatchEvent(new DisconnectEvent(this, daoSnapshot, disconnectException));
        disconnectException = null;
        configuration.getListenerManager().shutdown(this);
    }

    @Override
    public int compareTo(PircBotZ other) {
        return getBotId() - other.getBotId();
    }

    public State getState() {
        return state;
    }

    protected class InputProcessor extends Thread {

        @Override
        public void run() {
            while (socket != null && socket.isConnected()) {
                String line;
                try {
                    line = inputReader.readLine();
                } catch (InterruptedIOException iioe) {
                    sendRaw().rawLine("PING " + (System.currentTimeMillis() / 1000));
                    continue;
                } catch (IOException e) {
                    if (e instanceof SocketException && PircBotZ.this.getState() == PircBotZ.State.DISCONNECTED) {
                        return;
                    } else {
                        disconnectException = e;
                        line = null;
                    }
                }
                if (line == null) {
                    break;
                }
                try {
                    inputParser.handleLine(line);
                } catch (IOException | IrcException e) {
                }
                if (interrupted()) {
                    return;
                }
            }
            shutdown();
            if (configuration.isAutoReconnect() && !reconnectStopped) {
                try {
                    connect();
                } catch (IOException | IrcException ex) {
                }
            }
        }
    }

    protected static class BotShutdownHook extends Thread {

        protected final WeakReference<PircBotZ> thisBotRef;

        public BotShutdownHook(PircBotZ bot) {
            this.thisBotRef = new WeakReference<>(bot);
            setName("bot" + BOT_COUNT + "-shutdownhook");
        }

        @Override
        public void run() {
            PircBotZ thisBot = thisBotRef.get();
            if (thisBot != null && thisBot.getState() != PircBotZ.State.DISCONNECTED) {
                try {
                    thisBot.stopBotReconnect();
                    thisBot.sendIRC().quitServer();
                } finally {
                    if (thisBot.getState() != PircBotZ.State.DISCONNECTED) {
                        thisBot.shutdown(true);
                    }
                }
            }
        }
    }

    public static enum State {

        INIT,
        CONNECTED,
        DISCONNECTED
    }
}
