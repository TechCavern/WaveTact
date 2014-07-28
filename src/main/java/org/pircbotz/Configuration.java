package org.pircbotz;

import java.net.InetAddress;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.net.SocketFactory;
import org.pircbotz.hooks.CoreHooks;
import org.pircbotz.hooks.Listener;
import org.pircbotz.cap.CapHandler;
import org.pircbotz.cap.EnableCapHandler;
import org.pircbotz.managers.ListenerManager;
import org.pircbotz.managers.ThreadedListenerManager;
import org.apache.commons.lang3.Validate;

public class Configuration<PircBotZ> {

    private final boolean webIrcEnabled;
    private final String webIrcUsername;
    private final String webIrcHostname;
    private final InetAddress webIrcAddress;
    private final String webIrcPassword;
    private final String name;
    private final String login;
    private final String version;
    private final String finger;
    private final String realName;
    private final String channelPrefixes;
    private final boolean dccFilenameQuotes;
    private final List<Integer> dccPorts;
    private final InetAddress dccLocalAddress;
    private final int dccAcceptTimeout;
    private final int dccResumeAcceptTimeout;
    private final int dccTransferBufferSize;
    private final boolean dccPassiveRequest;
    private final String serverHostname;
    private final int serverPort;
    private final String serverPassword;
    private final SocketFactory socketFactory;
    private final InetAddress localAddress;
    private final Charset encoding;
    private final Locale locale;
    private final int socketTimeout;
    private final int maxLineLength;
    private final boolean autoSplitMessage;
    private final boolean autoNickChange;
    private final long messageDelay;
    private final boolean shutdownHookEnabled;
    private final Map<String, String> autoJoinChannels;
    private final String nickservPassword;
    private final boolean autoReconnect;
    private final ListenerManager listenerManager;
    private final boolean capEnabled;
    private final List<CapHandler> capHandlers;
    private final Map<Character, ChannelModeHandler> channelModeHandlers;
    private final BotFactory botFactory;

    private Configuration(Builder<PircBotZ> builder) {
        if (builder.isWebIrcEnabled()) {
            Validate.notNull(builder.getWebIrcAddress(), "Must specify WEBIRC address if enabled");
            Validate.notBlank(builder.getWebIrcHostname(), "Must specify WEBIRC hostname if enabled");
            Validate.notBlank(builder.getWebIrcUsername(), "Must specify WEBIRC username if enabled");
            Validate.notBlank(builder.getWebIrcPassword(), "Must specify WEBIRC password if enabled");
        }
        Validate.notNull(builder.getListenerManager());
        Validate.notBlank(builder.getName(), "Must specify name");
        Validate.notBlank(builder.getLogin(), "Must specify login");
        Validate.notBlank(builder.getRealName(), "Must specify realName");
        Validate.notBlank(builder.getChannelPrefixes(), "Must specify channel prefixes");
        Validate.isTrue(builder.getDccAcceptTimeout() > 0, "dccAcceptTimeout must be positive");
        Validate.isTrue(builder.getDccResumeAcceptTimeout() > 0, "dccResumeAcceptTimeout must be positive");
        Validate.isTrue(builder.getDccTransferBufferSize() > 0, "dccTransferBufferSize must be positive");
        Validate.notBlank(builder.getServerHostname(), "Must specify server hostname");
        Validate.isTrue(builder.getServerPort() > 0 && builder.getServerPort() <= 65535, "Port must be between 1 and 65535");
        Validate.notNull(builder.getSocketFactory(), "Must specify socket factory");
        Validate.notNull(builder.getEncoding(), "Must specify encoding");
        Validate.notNull(builder.getLocale(), "Must specify locale");
        Validate.isTrue(builder.getSocketTimeout() >= 0, "Socket timeout must be positive");
        Validate.isTrue(builder.getMaxLineLength() > 0, "Max line length must be positive");
        Validate.isTrue(builder.getMessageDelay() >= 0, "Message delay must be positive");
        if (builder.getNickservPassword() != null) {
            Validate.notEmpty(builder.getNickservPassword(), "Nickserv password cannot be empty");
        }
        Validate.notNull(builder.getListenerManager(), "Must specify listener manager");
        Validate.notNull(builder.getBotFactory(), "Must specify bot factory");
        this.webIrcEnabled = builder.isWebIrcEnabled();
        this.webIrcUsername = builder.getWebIrcUsername();
        this.webIrcHostname = builder.getWebIrcHostname();
        this.webIrcAddress = builder.getWebIrcAddress();
        this.webIrcPassword = builder.getWebIrcPassword();
        this.name = builder.getName();
        this.login = builder.getLogin();
        this.version = builder.getVersion();
        this.finger = builder.getFinger();
        this.realName = builder.getRealName();
        this.channelPrefixes = builder.getChannelPrefixes();
        this.dccFilenameQuotes = builder.isDccFilenameQuotes();
        this.dccPorts = new ArrayList<>(builder.getDccPorts());
        this.dccLocalAddress = builder.getDccLocalAddress();
        this.dccAcceptTimeout = builder.getDccAcceptTimeout();
        this.dccResumeAcceptTimeout = builder.getDccResumeAcceptTimeout();
        this.dccTransferBufferSize = builder.getDccTransferBufferSize();
        this.dccPassiveRequest = builder.isDccPassiveRequest();
        this.serverHostname = builder.getServerHostname();
        this.serverPort = builder.getServerPort();
        this.serverPassword = builder.getServerPassword();
        this.socketFactory = builder.getSocketFactory();
        this.localAddress = builder.getLocalAddress();
        this.encoding = builder.getEncoding();
        this.locale = builder.getLocale();
        this.socketTimeout = builder.getSocketTimeout();
        this.maxLineLength = builder.getMaxLineLength();
        this.autoSplitMessage = builder.isAutoSplitMessage();
        this.autoNickChange = builder.isAutoNickChange();
        this.messageDelay = builder.getMessageDelay();
        this.nickservPassword = builder.getNickservPassword();
        this.autoReconnect = builder.isAutoReconnect();
        this.listenerManager = builder.getListenerManager();
        this.autoJoinChannels = new HashMap<>(builder.getAutoJoinChannels());
        this.capEnabled = builder.isCapEnabled();
        this.capHandlers = new ArrayList<>(builder.getCapHandlers());
        channelModeHandlers = new HashMap<>();
        for (ChannelModeHandler curHandler : builder.getChannelModeHandlers()) {
            channelModeHandlers.put(curHandler.getMode(), curHandler);
        }
        this.shutdownHookEnabled = builder.isShutdownHookEnabled();
        this.botFactory = builder.getBotFactory();
    }

    public boolean isWebIrcEnabled() {
        return webIrcEnabled;
    }

    public String getWebIrcUsername() {
        return webIrcUsername;
    }

    public String getWebIrcHostname() {
        return webIrcHostname;
    }

    public InetAddress getWebIrcAddress() {
        return webIrcAddress;
    }

    public String getWebIrcPassword() {
        return webIrcPassword;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public String getVersion() {
        return version;
    }

    public String getFinger() {
        return finger;
    }

    public String getRealName() {
        return realName;
    }

    public String getChannelPrefixes() {
        return channelPrefixes;
    }

    public boolean isDccFilenameQuotes() {
        return dccFilenameQuotes;
    }

    public List<Integer> getDccPorts() {
        return dccPorts;
    }

    public InetAddress getDccLocalAddress() {
        return dccLocalAddress;
    }

    public int getDccAcceptTimeout() {
        return dccAcceptTimeout;
    }

    public int getDccResumeAcceptTimeout() {
        return dccResumeAcceptTimeout;
    }

    public int getDccTransferBufferSize() {
        return dccTransferBufferSize;
    }

    public boolean isDccPassiveRequest() {
        return dccPassiveRequest;
    }

    public String getServerHostname() {
        return serverHostname;
    }

    public int getServerPort() {
        return serverPort;
    }

    public String getServerPassword() {
        return serverPassword;
    }

    public SocketFactory getSocketFactory() {
        return socketFactory;
    }

    public InetAddress getLocalAddress() {
        return localAddress;
    }

    public Charset getEncoding() {
        return encoding;
    }

    public Locale getLocale() {
        return locale;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public int getMaxLineLength() {
        return maxLineLength;
    }

    public boolean isAutoSplitMessage() {
        return autoSplitMessage;
    }

    public boolean isAutoNickChange() {
        return autoNickChange;
    }

    public long getMessageDelay() {
        return messageDelay;
    }

    public boolean isShutdownHookEnabled() {
        return shutdownHookEnabled;
    }

    public Map<String, String> getAutoJoinChannels() {
        return autoJoinChannels;
    }

    public String getNickservPassword() {
        return nickservPassword;
    }

    public boolean isAutoReconnect() {
        return autoReconnect;
    }

    public ListenerManager getListenerManager() {
        return listenerManager;
    }

    public boolean isCapEnabled() {
        return capEnabled;
    }

    public List<CapHandler> getCapHandlers() {
        return capHandlers;
    }

    public Map<Character, ChannelModeHandler> getChannelModeHandlers() {
        return channelModeHandlers;
    }

    public BotFactory getBotFactory() {
        return botFactory;
    }

    public static class Builder<PircBotZ> {

        private boolean webIrcEnabled = false;
        private String webIrcUsername = null;
        private String webIrcHostname = null;
        private InetAddress webIrcAddress = null;
        private String webIrcPassword = null;
        private String name = "PircBotZ";
        private String login = "PircBotZ";
        private String version = "PircBotZ - WaveTact Edition";
        private String finger = "I would give you a finger, but I don't have any to spare";
        private String realName = version;
        private String channelPrefixes = "#&+!";
        private boolean dccFilenameQuotes = false;
        private List<Integer> dccPorts = new LinkedList<>();
        private InetAddress dccLocalAddress = null;
        private int dccAcceptTimeout = -1;
        private int dccResumeAcceptTimeout = -1;
        private int dccTransferBufferSize = 1024;
        private boolean dccPassiveRequest = false;
        private String serverHostname = null;
        private int serverPort = 6667;
        private String serverPassword = null;
        private SocketFactory socketFactory = SocketFactory.getDefault();
        private InetAddress localAddress = null;
        private Charset encoding = Charset.defaultCharset();
        private Locale locale = Locale.getDefault();
        private int socketTimeout = 1000 * 60 * 5;
        private int maxLineLength = 512;
        private boolean autoSplitMessage = true;
        private boolean autoNickChange = false;
        private long messageDelay = 1000;
        private boolean shutdownHookEnabled = true;
        private final Map<String, String> autoJoinChannels = new HashMap<>();
        private String nickservPassword;
        private boolean autoReconnect = false;
        private ListenerManager listenerManager = null;
        private boolean capEnabled = false;
        private final List<CapHandler> capHandlers = new LinkedList<>();
        private final List<ChannelModeHandler> channelModeHandlers = new LinkedList<>();
        private BotFactory botFactory = new BotFactory.DefaultBotFactory();

        public Builder() {
            capHandlers.add(new EnableCapHandler("multi-prefix", true));
            capHandlers.add(new EnableCapHandler("away-notify", true));
            channelModeHandlers.addAll(InputParser.getDefaultChannelModeHandlers());
        }

        public Builder(Configuration<PircBotZ> configuration) {
            this.webIrcEnabled = configuration.isWebIrcEnabled();
            this.webIrcUsername = configuration.getWebIrcUsername();
            this.webIrcHostname = configuration.getWebIrcHostname();
            this.webIrcAddress = configuration.getWebIrcAddress();
            this.webIrcPassword = configuration.getWebIrcPassword();
            this.name = configuration.getName();
            this.login = configuration.getLogin();
            this.version = configuration.getVersion();
            this.finger = configuration.getFinger();
            this.realName = configuration.getRealName();
            this.channelPrefixes = configuration.getChannelPrefixes();
            this.dccFilenameQuotes = configuration.isDccFilenameQuotes();
            this.dccPorts.addAll(configuration.getDccPorts());
            this.dccLocalAddress = configuration.getDccLocalAddress();
            this.dccAcceptTimeout = configuration.getDccAcceptTimeout();
            this.dccResumeAcceptTimeout = configuration.getDccResumeAcceptTimeout();
            this.dccTransferBufferSize = configuration.getDccTransferBufferSize();
            this.dccPassiveRequest = configuration.isDccPassiveRequest();
            this.serverHostname = configuration.getServerHostname();
            this.serverPort = configuration.getServerPort();
            this.serverPassword = configuration.getServerPassword();
            this.socketFactory = configuration.getSocketFactory();
            this.localAddress = configuration.getLocalAddress();
            this.encoding = configuration.getEncoding();
            this.locale = configuration.getLocale();
            this.socketTimeout = configuration.getSocketTimeout();
            this.maxLineLength = configuration.getMaxLineLength();
            this.autoSplitMessage = configuration.isAutoSplitMessage();
            this.autoNickChange = configuration.isAutoNickChange();
            this.messageDelay = configuration.getMessageDelay();
            this.listenerManager = configuration.getListenerManager();
            this.nickservPassword = configuration.getNickservPassword();
            this.autoReconnect = configuration.isAutoReconnect();
            this.autoJoinChannels.putAll(configuration.getAutoJoinChannels());
            this.capEnabled = configuration.isCapEnabled();
            this.capHandlers.addAll(configuration.getCapHandlers());
            this.channelModeHandlers.addAll(configuration.getChannelModeHandlers().values());
            this.shutdownHookEnabled = configuration.isShutdownHookEnabled();
            this.botFactory = configuration.getBotFactory();
        }

        public Builder(Builder<PircBotZ> otherBuilder) {
            this.webIrcEnabled = otherBuilder.isWebIrcEnabled();
            this.webIrcUsername = otherBuilder.getWebIrcUsername();
            this.webIrcHostname = otherBuilder.getWebIrcHostname();
            this.webIrcAddress = otherBuilder.getWebIrcAddress();
            this.webIrcPassword = otherBuilder.getWebIrcPassword();
            this.name = otherBuilder.getName();
            this.login = otherBuilder.getLogin();
            this.version = otherBuilder.getVersion();
            this.finger = otherBuilder.getFinger();
            this.realName = otherBuilder.getRealName();
            this.channelPrefixes = otherBuilder.getChannelPrefixes();
            this.dccFilenameQuotes = otherBuilder.isDccFilenameQuotes();
            this.dccPorts.addAll(otherBuilder.getDccPorts());
            this.dccLocalAddress = otherBuilder.getDccLocalAddress();
            this.dccAcceptTimeout = otherBuilder.getDccAcceptTimeout();
            this.dccResumeAcceptTimeout = otherBuilder.getDccResumeAcceptTimeout();
            this.dccTransferBufferSize = otherBuilder.getDccTransferBufferSize();
            this.dccPassiveRequest = otherBuilder.isDccPassiveRequest();
            this.serverHostname = otherBuilder.getServerHostname();
            this.serverPort = otherBuilder.getServerPort();
            this.serverPassword = otherBuilder.getServerPassword();
            this.socketFactory = otherBuilder.getSocketFactory();
            this.localAddress = otherBuilder.getLocalAddress();
            this.encoding = otherBuilder.getEncoding();
            this.locale = otherBuilder.getLocale();
            this.socketTimeout = otherBuilder.getSocketTimeout();
            this.maxLineLength = otherBuilder.getMaxLineLength();
            this.autoSplitMessage = otherBuilder.isAutoSplitMessage();
            this.autoNickChange = otherBuilder.isAutoNickChange();
            this.messageDelay = otherBuilder.getMessageDelay();
            this.listenerManager = otherBuilder.getListenerManager();
            this.nickservPassword = otherBuilder.getNickservPassword();
            this.autoReconnect = otherBuilder.isAutoReconnect();
            this.autoJoinChannels.putAll(otherBuilder.getAutoJoinChannels());
            this.capEnabled = otherBuilder.isCapEnabled();
            this.capHandlers.addAll(otherBuilder.getCapHandlers());
            this.channelModeHandlers.addAll(otherBuilder.getChannelModeHandlers());
            this.shutdownHookEnabled = otherBuilder.isShutdownHookEnabled();
            this.botFactory = otherBuilder.getBotFactory();
        }

        public Builder<PircBotZ> setWebIrcEnabled(boolean webIrcEnabled) {
            this.webIrcEnabled = webIrcEnabled;
            return this;
        }

        public Builder<PircBotZ> setWebIrcUsername(String webIrcUsername) {
            this.webIrcUsername = webIrcUsername;
            return this;
        }

        public Builder<PircBotZ> setWebIrcHostname(String webIrcHostname) {
            this.webIrcHostname = webIrcHostname;
            return this;
        }

        public Builder<PircBotZ> setWebIrcAddress(InetAddress webIrcAddress) {
            this.webIrcAddress = webIrcAddress;
            return this;
        }

        public Builder<PircBotZ> setWebIrcPassword(String webIrcPassword) {
            this.webIrcPassword = webIrcPassword;
            return this;
        }

        public Builder<PircBotZ> setName(String name) {
            this.name = name;
            return this;
        }

        public Builder<PircBotZ> setLogin(String login) {
            this.login = login;
            return this;
        }

        public Builder<PircBotZ> setVersion(String version) {
            this.version = version;
            return this;
        }

        public Builder<PircBotZ> setFinger(String finger) {
            this.finger = finger;
            return this;
        }

        public Builder<PircBotZ> setRealName(String realName) {
            this.realName = realName;
            return this;
        }

        public Builder<PircBotZ> setChannelPrefixes(String channelPrefixes) {
            this.channelPrefixes = channelPrefixes;
            return this;
        }

        public Builder<PircBotZ> setDccFilenameQuotes(boolean dccFilenameQuotes) {
            this.dccFilenameQuotes = dccFilenameQuotes;
            return this;
        }

        public Builder<PircBotZ> setDccPorts(List<Integer> dccPorts) {
            this.dccPorts = dccPorts;
            return this;
        }

        public Builder<PircBotZ> setDccLocalAddress(InetAddress dccLocalAddress) {
            this.dccLocalAddress = dccLocalAddress;
            return this;
        }

        public Builder<PircBotZ> setDccAcceptTimeout(int dccAcceptTimeout) {
            this.dccAcceptTimeout = dccAcceptTimeout;
            return this;
        }

        public Builder<PircBotZ> setDccResumeAcceptTimeout(int dccResumeAcceptTimeout) {
            this.dccResumeAcceptTimeout = dccResumeAcceptTimeout;
            return this;
        }

        public Builder<PircBotZ> setDccTransferBufferSize(int dccTransferBufferSize) {
            this.dccTransferBufferSize = dccTransferBufferSize;
            return this;
        }

        public Builder<PircBotZ> setDccPassiveRequest(boolean dccPassiveRequest) {
            this.dccPassiveRequest = dccPassiveRequest;
            return this;
        }

        public Builder<PircBotZ> setServerHostname(String serverHostname) {
            this.serverHostname = serverHostname;
            return this;
        }

        public Builder<PircBotZ> setServerPort(int serverPort) {
            this.serverPort = serverPort;
            return this;
        }

        public Builder<PircBotZ> setServerPassword(String serverPassword) {
            this.serverPassword = serverPassword;
            return this;
        }

        public Builder<PircBotZ> setSocketFactory(SocketFactory socketFactory) {
            this.socketFactory = socketFactory;
            return this;
        }

        public Builder<PircBotZ> setLocalAddress(InetAddress localAddress) {
            this.localAddress = localAddress;
            return this;
        }

        public Builder<PircBotZ> setEncoding(Charset encoding) {
            this.encoding = encoding;
            return this;
        }

        public Builder<PircBotZ> setLocale(Locale locale) {
            this.locale = locale;
            return this;
        }

        public Builder<PircBotZ> setSocketTimeout(int socketTimeout) {
            this.socketTimeout = socketTimeout;
            return this;
        }

        public Builder<PircBotZ> setMaxLineLength(int maxLineLength) {
            this.maxLineLength = maxLineLength;
            return this;
        }

        public Builder<PircBotZ> setAutoSplitMessage(boolean autoSplitMessage) {
            this.autoSplitMessage = autoSplitMessage;
            return this;
        }

        public Builder<PircBotZ> setAutoNickChange(boolean autoNickChange) {
            this.autoNickChange = autoNickChange;
            return this;
        }

        public Builder<PircBotZ> setMessageDelay(long messageDelay) {
            this.messageDelay = messageDelay;
            return this;
        }

        public Builder<PircBotZ> setShutdownHookEnabled(boolean shutdownHookEnabled) {
            this.shutdownHookEnabled = shutdownHookEnabled;
            return this;
        }

        public Builder<PircBotZ> setNickservPassword(String nickservPassword) {
            this.nickservPassword = nickservPassword;
            return this;
        }

        public Builder<PircBotZ> setAutoReconnect(boolean autoReconnect) {
            this.autoReconnect = autoReconnect;
            return this;
        }

        public Builder<PircBotZ> setCapEnabled(boolean capEnabled) {
            this.capEnabled = capEnabled;
            return this;
        }

        public Builder<PircBotZ> setBotFactory(BotFactory botFactory) {
            this.botFactory = botFactory;
            return this;
        }

        public InetAddress getDccLocalAddress() {
            return (dccLocalAddress != null) ? dccLocalAddress : localAddress;
        }

        public int getDccAcceptTimeout() {
            return (dccAcceptTimeout != -1) ? dccAcceptTimeout : socketTimeout;
        }

        public int getDccResumeAcceptTimeout() {
            return (dccResumeAcceptTimeout != -1) ? dccResumeAcceptTimeout : getDccAcceptTimeout();
        }

        public Builder<PircBotZ> addCapHandler(CapHandler handler) {
            getCapHandlers().add(handler);
            return this;
        }

        public boolean isWebIrcEnabled() {
            return webIrcEnabled;
        }

        public String getWebIrcUsername() {
            return webIrcUsername;
        }

        public String getWebIrcHostname() {
            return webIrcHostname;
        }

        public InetAddress getWebIrcAddress() {
            return webIrcAddress;
        }

        public String getWebIrcPassword() {
            return webIrcPassword;
        }

        public String getName() {
            return name;
        }

        public String getLogin() {
            return login;
        }

        public String getVersion() {
            return version;
        }

        public String getFinger() {
            return finger;
        }

        public String getRealName() {
            return realName;
        }

        public String getChannelPrefixes() {
            return channelPrefixes;
        }

        public boolean isDccFilenameQuotes() {
            return dccFilenameQuotes;
        }

        public List<Integer> getDccPorts() {
            return dccPorts;
        }

        public int getDccTransferBufferSize() {
            return dccTransferBufferSize;
        }

        public boolean isDccPassiveRequest() {
            return dccPassiveRequest;
        }

        public String getServerHostname() {
            return serverHostname;
        }

        public int getServerPort() {
            return serverPort;
        }

        public String getServerPassword() {
            return serverPassword;
        }

        public SocketFactory getSocketFactory() {
            return socketFactory;
        }

        public InetAddress getLocalAddress() {
            return localAddress;
        }

        public Charset getEncoding() {
            return encoding;
        }

        public Locale getLocale() {
            return locale;
        }

        public int getSocketTimeout() {
            return socketTimeout;
        }

        public int getMaxLineLength() {
            return maxLineLength;
        }

        public boolean isAutoSplitMessage() {
            return autoSplitMessage;
        }

        public boolean isAutoNickChange() {
            return autoNickChange;
        }

        public long getMessageDelay() {
            return messageDelay;
        }

        public boolean isShutdownHookEnabled() {
            return shutdownHookEnabled;
        }

        public Map<String, String> getAutoJoinChannels() {
            return autoJoinChannels;
        }

        public String getNickservPassword() {
            return nickservPassword;
        }

        public boolean isAutoReconnect() {
            return autoReconnect;
        }

        public boolean isCapEnabled() {
            return capEnabled;
        }

        public List<CapHandler> getCapHandlers() {
            return capHandlers;
        }

        public List<ChannelModeHandler> getChannelModeHandlers() {
            return channelModeHandlers;
        }

        public BotFactory getBotFactory() {
            return botFactory;
        }

        public Builder<PircBotZ> addListener(Listener listener) {
            getListenerManager().addListener(listener);
            return this;
        }

        public Builder<PircBotZ> addAutoJoinChannel(String channel) {
            getAutoJoinChannels().put(channel, "");
            return this;
        }

        public Builder<PircBotZ> addAutoJoinChannel(String channel, String key) {
            getAutoJoinChannels().put(channel, key);
            return this;
        }

        public Builder<PircBotZ> setServer(String hostname, int port) {
            return setServerHostname(hostname).setServerPort(port);
        }

        public Builder<PircBotZ> setServer(String hostname, int port, String password) {
            return setServer(hostname, port).setServerPassword(password);
        }

        public Builder<PircBotZ> setListenerManager(ListenerManager listenerManager) {
            this.listenerManager = listenerManager;
            for (Listener curListener : this.listenerManager.getListeners()) {
                if (curListener instanceof CoreHooks) {
                    return this;
                }
            }
            listenerManager.addListener(new CoreHooks());
            return this;
        }

        public ListenerManager getListenerManager() {
            if (listenerManager == null) {
                setListenerManager(new ThreadedListenerManager());
            }
            return listenerManager;
        }

        public Configuration<PircBotZ> buildConfiguration() {
            return new Configuration<>(this);
        }

        public Configuration<PircBotZ> buildForServer(String hostname) {
            return new Builder<>(this)
                    .setServerHostname(serverHostname)
                    .buildConfiguration();
        }

        public Configuration<PircBotZ> buildForServer(String hostname, int port) {
            return new Builder<>(this)
                    .setServerHostname(serverHostname)
                    .setServerPort(serverPort)
                    .buildConfiguration();
        }

        public Configuration<PircBotZ> buildForServer(String hostname, int port, String password) {
            return new Builder<>(this)
                    .setServerHostname(serverHostname)
                    .setServerPort(serverPort)
                    .setServerPassword(serverPassword)
                    .buildConfiguration();
        }
    }
}
