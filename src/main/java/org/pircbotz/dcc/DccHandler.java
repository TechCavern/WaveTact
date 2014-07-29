package org.pircbotz.dcc;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.pircbotz.PircBotZ;
import org.pircbotz.User;
import org.pircbotz.Utils;
import org.pircbotz.exception.DccException;
import org.pircbotz.hooks.events.IncomingChatRequestEvent;
import org.pircbotz.hooks.events.IncomingFileTransferEvent;
import org.apache.commons.lang3.Validate;

public class DccHandler implements Closeable {

    private static final Random TOKEN_RANDOM = new SecureRandom();
    private static final int TOKEN_RANDOM_MAX = 20000;
    private final PircBotZ bot;
    private final Map<PendingRecieveFileTransfer, CountDownLatch> pendingReceiveTransfers = new HashMap<>();
    private final List<PendingSendFileTransfer> pendingSendTransfers = new ArrayList<>();
    private final Map<PendingSendFileTransferPassive, CountDownLatch> pendingSendPassiveTransfers = new HashMap<>();
    private final Map<PendingSendChatPassive, CountDownLatch> pendingSendPassiveChat = new HashMap<>();
    private boolean shuttingDown = false;

    public DccHandler(PircBotZ bot) {
        this.bot = bot;
    }

    public boolean processDcc(final User user, String request) throws IOException {
        List<String> requestParts = tokenizeDccRequest(request);
        String type = requestParts.get(1);
        switch (type) {
            case "SEND": {
                String rawFilename = requestParts.get(2);
                final String safeFilename = (rawFilename.startsWith("\"") && rawFilename.endsWith("\""))
                        ? rawFilename.substring(1, rawFilename.length() - 1) : rawFilename;
                InetAddress address = integerToAddress(requestParts.get(3));
                int port = Integer.parseInt(requestParts.get(4));
                long size = Integer.parseInt(Utils.tryGetIndex(requestParts, 5, "-1"));
                String transferToken = Utils.tryGetIndex(requestParts, 6, null);
                if (transferToken != null) {
                    synchronized (pendingSendPassiveTransfers) {
                        Iterator<Map.Entry<PendingSendFileTransferPassive, CountDownLatch>> pendingItr = pendingSendPassiveTransfers.entrySet().iterator();
                        while (pendingItr.hasNext()) {
                            Map.Entry<PendingSendFileTransferPassive, CountDownLatch> curEntry = pendingItr.next();
                            PendingSendFileTransferPassive transfer = curEntry.getKey();
                            if (transfer.getUser() == user && transfer.getFilename().equals(rawFilename)
                                    && transfer.getTransferToken().equals(transferToken)) {
                                transfer.setReceiverAddress(address);
                                transfer.setReceiverPort(port);
                                curEntry.getValue().countDown();
                                pendingItr.remove();
                                return true;
                            }
                        }
                    }
                }
                if (port == 0 || transferToken != null) {
                    bot.getConfiguration().getListenerManager().dispatchEvent(new IncomingFileTransferEvent(bot, user, rawFilename, safeFilename, address, port, size, transferToken, true));
                } else {
                    bot.getConfiguration().getListenerManager().dispatchEvent(new IncomingFileTransferEvent(bot, user, rawFilename, safeFilename, address, port, size, transferToken, false));
                }
                break;
            }
            case "RESUME": {
                String filename = requestParts.get(2);
                int port = Integer.parseInt(requestParts.get(3));
                long position = Integer.parseInt(requestParts.get(4));
                if (port == 0) {
                    String transferToken = requestParts.get(5);
                    synchronized (pendingSendPassiveTransfers) {
                        Iterator<Map.Entry<PendingSendFileTransferPassive, CountDownLatch>> pendingItr = pendingSendPassiveTransfers.entrySet().iterator();
                        while (pendingItr.hasNext()) {
                            Map.Entry<PendingSendFileTransferPassive, CountDownLatch> curEntry = pendingItr.next();
                            PendingSendFileTransferPassive transfer = curEntry.getKey();
                            if (transfer.getUser() == user && transfer.getFilename().equals(filename)
                                    && transfer.getTransferToken().equals(transferToken)) {
                                transfer.setStartPosition(position);

                                return true;
                            }
                        }
                    }
                } else {
                    synchronized (pendingSendTransfers) {
                        Iterator<PendingSendFileTransfer> pendingItr = pendingSendTransfers.iterator();
                        while (pendingItr.hasNext()) {
                            PendingSendFileTransfer transfer = pendingItr.next();
                            if (transfer.getUser() == user && transfer.getFilename().equals(filename)
                                    && transfer.getPort() == port) {
                                transfer.setPosition(position);

                                return true;
                            }
                        }
                    }
                }
                throw new DccException(DccException.Reason.UnknownFileTransferResume, user, "Transfer line: " + request);
            }
            case "ACCEPT": {
                String filename = requestParts.get(2);
                int dataPosition = (requestParts.size() == 5) ? 3 : 4;
                long position = Integer.parseInt(requestParts.get(dataPosition));
                String transferToken = requestParts.get(dataPosition + 1);
                synchronized (pendingReceiveTransfers) {
                    Iterator<Map.Entry<PendingRecieveFileTransfer, CountDownLatch>> pendingItr = pendingReceiveTransfers.entrySet().iterator();
                    while (pendingItr.hasNext()) {
                        Map.Entry<PendingRecieveFileTransfer, CountDownLatch> curEntry = pendingItr.next();
                        IncomingFileTransferEvent transferEvent = curEntry.getKey().getEvent();
                        if (transferEvent.getUser() == user && transferEvent.getRawFilename().equals(filename)
                                && transferEvent.getTransferToken().equals(transferToken)) {
                            curEntry.getKey().setPosition(position);

                            curEntry.getValue().countDown();
                            pendingItr.remove();
                            return true;
                        }
                    }
                }
                break;
            }
            case "CHAT": {
                InetAddress address = integerToAddress(requestParts.get(3));
                int port = Integer.parseInt(requestParts.get(4));
                String chatToken = Utils.tryGetIndex(requestParts, 5, null);
                if (chatToken != null) {
                    synchronized (pendingSendPassiveChat) {
                        Iterator<Map.Entry<PendingSendChatPassive, CountDownLatch>> pendingItr = pendingSendPassiveChat.entrySet().iterator();
                        while (pendingItr.hasNext()) {
                            Map.Entry<PendingSendChatPassive, CountDownLatch> curEntry = pendingItr.next();
                            PendingSendChatPassive pendingChat = curEntry.getKey();
                            if (pendingChat.getUser() == user && pendingChat.getChatToken().equals(chatToken)) {
                                pendingChat.setReceiverAddress(address);
                                pendingChat.setReceiverPort(port);
                                curEntry.getValue().countDown();
                                pendingItr.remove();
                                return true;
                            }
                        }
                    }
                }
                if (port == 0 && chatToken != null) {
                    bot.getConfiguration().getListenerManager().dispatchEvent(new IncomingChatRequestEvent(bot, user, address, port, chatToken, true));
                } else {
                    bot.getConfiguration().getListenerManager().dispatchEvent(new IncomingChatRequestEvent(bot, user, address, port, chatToken, false));
                }
                break;
            }
            default:
                return false;
        }
        return true;
    }

    public ReceiveChat acceptChatRequest(IncomingChatRequestEvent event) throws IOException {
        Validate.notNull(event, "Event cannot be null");
        if (event.isPassive()) {
            try (ServerSocket serverSocket = createServerSocket(event.getUser())) {
                bot.sendDCC().chatPassiveAccept(event.getUser().getNick(), serverSocket.getInetAddress(), serverSocket.getLocalPort(), event.getChatToken());
                Socket userSocket = serverSocket.accept();
                return bot.getConfiguration().getBotFactory().createReceiveChat(bot, event.getUser(), userSocket);
            }
        } else {
            return bot.getConfiguration().getBotFactory().createReceiveChat(bot, event.getUser(), new Socket(event.getChatAddress(), event.getChatPort()));
        }
    }

    public ReceiveFileTransfer acceptFileTransfer(IncomingFileTransferEvent event, File destination) throws IOException {
        Validate.notNull(event, "Event cannot be null");
        Validate.notNull(destination, "Destination file cannot be null");
        return acceptFileTransfer(event, destination, 0);
    }

    public ReceiveFileTransfer acceptFileTransferResume(IncomingFileTransferEvent event, File destination, long startPosition) throws IOException, InterruptedException, DccException {
        Validate.notNull(event, "Event cannot be null");
        Validate.notNull(destination, "Destination file cannot be null");
        Validate.isTrue(startPosition >= 0, "Start position %s must be positive", startPosition);
        CountDownLatch countdown = new CountDownLatch(1);
        PendingRecieveFileTransfer pendingTransfer = new PendingRecieveFileTransfer(event);
        synchronized (pendingReceiveTransfers) {
            pendingReceiveTransfers.put(pendingTransfer, countdown);
        }
        if (event.isPassive()) {
            bot.sendDCC().filePassiveResumeRequest(event.getUser().getNick(), event.getRawFilename(), startPosition, event.getTransferToken());
        } else {
            bot.sendDCC().fileResumeRequest(event.getUser().getNick(), event.getRawFilename(), event.getPort(), startPosition);
        }
        if (!countdown.await(bot.getConfiguration().getDccResumeAcceptTimeout(), TimeUnit.MILLISECONDS)) {
            throw new DccException(DccException.Reason.FileTransferResumeTimeout, event.getUser(), "Event: " + event);
        }
        if (shuttingDown) {
            throw new DccException(DccException.Reason.FileTransferResumeCancelled, event.getUser(), "Transfer " + event + " canceled due to bot shutting down");
        }
        if (pendingTransfer.getPosition() != startPosition) {
        }
        return acceptFileTransfer(event, destination, pendingTransfer.getPosition());
    }

    protected ReceiveFileTransfer acceptFileTransfer(IncomingFileTransferEvent event, File destination, long startPosition) throws IOException {
        Validate.notNull(event, "Event cannot be null");
        Validate.notNull(destination, "Destination file cannot be null");
        Validate.isTrue(startPosition >= 0, "Start position %s must be positive", startPosition);
        if (event.isPassive()) {
            Socket userSocket;
            try (ServerSocket serverSocket = createServerSocket(event.getUser())) {
                bot.sendDCC().filePassiveAccept(event.getUser().getNick(), event.getRawFilename(), serverSocket.getInetAddress(), serverSocket.getLocalPort(), event.getFilesize(), event.getTransferToken());
                userSocket = serverSocket.accept();
            }
            return bot.getConfiguration().getBotFactory().createReceiveFileTransfer(bot, userSocket, event.getUser(), destination, startPosition);
        } else {
            Socket userSocket = new Socket(event.getAddress(), event.getPort(), getRealDccAddress(), 0);
            return bot.getConfiguration().getBotFactory().createReceiveFileTransfer(bot, userSocket, event.getUser(), destination, startPosition);
        }
    }

    public SendChat sendChat(User receiver) throws IOException, InterruptedException {
        return sendChat(receiver, bot.getConfiguration().isDccPassiveRequest());
    }

    public SendChat sendChat(User receiver, boolean passive) throws IOException, InterruptedException {
        Validate.notNull(receiver, "Receiver user cannot be null");
        int dccAcceptTimeout = bot.getConfiguration().getDccAcceptTimeout();
        if (passive) {
            String chatToken = Integer.toString(TOKEN_RANDOM.nextInt(TOKEN_RANDOM_MAX));
            PendingSendChatPassive pendingChat = new PendingSendChatPassive(receiver, chatToken);
            CountDownLatch countdown = new CountDownLatch(1);
            synchronized (pendingSendPassiveChat) {
                pendingSendPassiveChat.put(pendingChat, countdown);
            }
            bot.sendDCC().chatPassiveRequest(receiver.getNick(), getRealDccAddress(), chatToken);
            if (!countdown.await(dccAcceptTimeout, TimeUnit.MILLISECONDS)) {
                throw new DccException(DccException.Reason.ChatTimeout, receiver, "");
            }
            if (shuttingDown) {
                throw new DccException(DccException.Reason.ChatCancelled, receiver, "");
            }
            Socket chatSocket = new Socket(pendingChat.getReceiverAddress(), pendingChat.getReceiverPort());
            return bot.getConfiguration().getBotFactory().createSendChat(bot, receiver, chatSocket);
        } else {
            try (ServerSocket serverSocket = createServerSocket(receiver)) {
                serverSocket.setSoTimeout(dccAcceptTimeout);
                bot.sendDCC().chatRequest(receiver.getNick(), serverSocket.getInetAddress(), serverSocket.getLocalPort());
                Socket userSocket = serverSocket.accept();
                return bot.getConfiguration().getBotFactory().createSendChat(bot, receiver, userSocket);
            }
        }
    }

    public SendFileTransfer sendFile(File file, User receiver) throws IOException, DccException, InterruptedException {
        return sendFile(file, receiver, bot.getConfiguration().isDccPassiveRequest());
    }

    public SendFileTransfer sendFile(File file, User receiver, boolean passive) throws IOException, DccException, InterruptedException {
        Validate.notNull(file, "Source file cannot be null");
        Validate.notNull(receiver, "Receiver cannot be null");
        Validate.isTrue(file.exists(), "File must exist");
        String safeFilename = file.getName();
        if (safeFilename.contains(" ")) {
            if (bot.getConfiguration().isDccFilenameQuotes()) {
                safeFilename = "\"" + safeFilename + "\"";
            } else {
                safeFilename = safeFilename.replace(" ", "_");
            }
        }
        if (passive) {
            String transferToken = Integer.toString(TOKEN_RANDOM.nextInt(TOKEN_RANDOM_MAX));
            CountDownLatch countdown = new CountDownLatch(1);
            PendingSendFileTransferPassive pendingPassiveTransfer = new PendingSendFileTransferPassive(receiver, safeFilename, transferToken);
            synchronized (pendingSendTransfers) {
                pendingSendPassiveTransfers.put(pendingPassiveTransfer, countdown);
            }
            bot.sendDCC().filePassiveRequest(receiver.getNick(), safeFilename, getRealDccAddress(), file.length(), transferToken);
            if (!countdown.await(bot.getConfiguration().getDccAcceptTimeout(), TimeUnit.MILLISECONDS)) {
                throw new DccException(DccException.Reason.FileTransferTimeout, receiver, "File: " + file.getAbsolutePath());
            }
            if (shuttingDown) {
                throw new DccException(DccException.Reason.FileTransferCancelled, receiver, "Transfer of file " + file.getAbsolutePath()
                        + " canceled due to bot shutdown");
            }
            Socket transferSocket = new Socket(pendingPassiveTransfer.getReceiverAddress(), pendingPassiveTransfer.getReceiverPort());
            return bot.getConfiguration().getBotFactory().createSendFileTransfer(bot, transferSocket, receiver, file, pendingPassiveTransfer.getStartPosition());
        } else {
            try (ServerSocket serverSocket = createServerSocket(receiver)) {
                PendingSendFileTransfer pendingSendFileTransfer = new PendingSendFileTransfer(receiver, safeFilename, serverSocket.getLocalPort());
                synchronized (pendingSendTransfers) {
                    pendingSendTransfers.add(pendingSendFileTransfer);
                }
                bot.sendDCC().fileRequest(receiver.getNick(), safeFilename, serverSocket.getInetAddress(), serverSocket.getLocalPort(), file.length());
                Socket userSocket = serverSocket.accept();
                return bot.getConfiguration().getBotFactory().createSendFileTransfer(bot, userSocket, receiver, file, pendingSendFileTransfer.getPosition());
            }
        }
    }

    public InetAddress getRealDccAddress() {
        InetAddress address = bot.getConfiguration().getDccLocalAddress();
        return (address != null) ? address : bot.getLocalAddress();
    }

    protected ServerSocket createServerSocket(User user) throws IOException, DccException {
        InetAddress address = bot.getConfiguration().getDccLocalAddress();
        List<Integer> dccPorts = bot.getConfiguration().getDccPorts();
        if (address == null) {
            address = bot.getLocalAddress();
        }
        ServerSocket ss = null;
        if (dccPorts.isEmpty()) {
            ss = new ServerSocket(0, 1, address);
        } else {
            for (int currentPort : dccPorts) {
                try {
                    ss = new ServerSocket(currentPort, 1, address);
                    break;
                } catch (IOException e) {
                }
            }
            if (ss == null) {
                throw new DccException(DccException.Reason.DccPortsInUse, user, "Ports " + dccPorts + " are in use.");
            }
        }
        return ss;
    }

    protected static List<String> tokenizeDccRequest(String request) {
        int quotesIndexBegin = request.indexOf('"');
        if (quotesIndexBegin == -1) {
            return Utils.tokenizeLine(request);
        }
        int quotesIndexEnd = request.lastIndexOf('"');
        List<String> stringParts = new LinkedList<>();
        int pos = 0, end;
        while ((end = request.indexOf(' ', pos)) >= 0) {
            if (pos >= quotesIndexBegin && end < quotesIndexEnd) {
                stringParts.add(request.substring(quotesIndexBegin, quotesIndexEnd + 1));
                pos = quotesIndexEnd + 2;
                continue;
            }
            stringParts.add(request.substring(pos, end));
            pos = end + 1;
            if (request.charAt(pos) == ':') {
                stringParts.add(request.substring(pos + 1));
                return stringParts;
            }
        }
        stringParts.add(request.substring(pos));
        return stringParts;
    }

    @Override
    public void close() {
        shuttingDown = true;
        for (CountDownLatch curCountdown : pendingReceiveTransfers.values()) {
            curCountdown.countDown();
        }
        for (CountDownLatch curCountdown : pendingSendPassiveTransfers.values()) {
            curCountdown.countDown();
        }
    }

    public static String addressToInteger(InetAddress address) {
        return new BigInteger(1, address.getAddress()).toString();
    }

    public static InetAddress integerToAddress(String rawInteger) {
        BigInteger bigIp = new BigInteger(rawInteger);
        byte[] addressBytes = bigIp.toByteArray();
        if (addressBytes.length == 5) {
            addressBytes = Arrays.copyOfRange(addressBytes, 1, 5);
        } else if (addressBytes.length < 4) {
            byte[] newAddressBytes = new byte[4];
            newAddressBytes[3] = addressBytes[0];
            newAddressBytes[2] = (addressBytes.length > 1) ? addressBytes[1] : (byte) 0;
            newAddressBytes[1] = (addressBytes.length > 2) ? addressBytes[2] : (byte) 0;
            newAddressBytes[0] = (addressBytes.length > 3) ? addressBytes[3] : (byte) 0;
            addressBytes = newAddressBytes;
        } else if (addressBytes.length == 17) {
            addressBytes = Arrays.copyOfRange(addressBytes, 1, 17);
        }
        try {
            return InetAddress.getByAddress(addressBytes);
        } catch (UnknownHostException ex) {
            throw new RuntimeException("Can't get InetAdrress version of int IP address " + rawInteger + " (bytes: " + Arrays.toString(addressBytes) + ")", ex);
        }
    }

    private static class PendingRecieveFileTransfer {

        private final IncomingFileTransferEvent event;
        private long position;

        public PendingRecieveFileTransfer(IncomingFileTransferEvent event) {
            this.event = event;
            this.position = 0;
        }

        public IncomingFileTransferEvent getEvent() {
            return event;
        }

        public long getPosition() {
            return position;
        }

        public void setPosition(long position) {
            this.position = position;
        }
    }

    private static class PendingSendFileTransfer {

        private final User user;
        private final String filename;
        private final int port;
        private long position = 0;

        public PendingSendFileTransfer(User user, String filename, int port) {
            this.user = user;
            this.filename = filename;
            this.port = port;
        }

        public User getUser() {
            return user;
        }

        public String getFilename() {
            return filename;
        }

        public int getPort() {
            return port;
        }

        public long getPosition() {
            return position;
        }

        public void setPosition(long position) {
            this.position = position;
        }
    }

    private static class PendingSendFileTransferPassive {

        private final User user;
        private final String filename;
        private final String transferToken;
        private long startPosition = 0;
        private InetAddress receiverAddress;
        private int receiverPort;

        public PendingSendFileTransferPassive(User user, String filename, String transferToken) {
            this.user = user;
            this.filename = filename;
            this.transferToken = transferToken;
        }

        public User getUser() {
            return user;
        }

        public String getFilename() {
            return filename;
        }

        public String getTransferToken() {
            return transferToken;
        }

        public long getStartPosition() {
            return startPosition;
        }

        public InetAddress getReceiverAddress() {
            return receiverAddress;
        }

        public int getReceiverPort() {
            return receiverPort;
        }

        public void setStartPosition(long startPosition) {
            this.startPosition = startPosition;
        }

        public void setReceiverAddress(InetAddress receiverAddress) {
            this.receiverAddress = receiverAddress;
        }

        public void setReceiverPort(int receiverPort) {
            this.receiverPort = receiverPort;
        }
    }

    private static class PendingSendChatPassive {

        private final User user;
        private final String chatToken;
        private InetAddress receiverAddress;
        private int receiverPort;

        public PendingSendChatPassive(User user, String chatToken) {
            this.user = user;
            this.chatToken = chatToken;
        }

        public User getUser() {
            return user;
        }

        public String getChatToken() {
            return chatToken;
        }

        public InetAddress getReceiverAddress() {
            return receiverAddress;
        }

        public int getReceiverPort() {
            return receiverPort;
        }

        public void setReceiverAddress(InetAddress receiverAddress) {
            this.receiverAddress = receiverAddress;
        }

        public void setReceiverPort(int receiverPort) {
            this.receiverPort = receiverPort;
        }
    }
}
