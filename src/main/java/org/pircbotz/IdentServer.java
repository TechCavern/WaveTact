package org.pircbotz;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;

public class IdentServer extends Thread implements AutoCloseable {

    private final Charset encoding;
    private final ServerSocket serverSocket;
    private final List<IdentEntry> identEntries = new LinkedList<>();
    private final String ip;
    private final int port;

    public IdentServer(Charset encoding, String ip, int port) throws IOException {
        super();
        this.ip = ip;
        this.port = port;
        this.encoding = encoding;
        this.serverSocket = new ServerSocket();
        this.setName("IdentServer");
    }

    public Charset getEncoding() {
        return encoding;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public List<IdentEntry> getIdentEntries() {
        return identEntries;
    }

    @Override
    public void start() {
        try {
            if (ip == null || ip.isEmpty()) {
                serverSocket.bind(new InetSocketAddress(port));
            } else {
                serverSocket.bind(new InetSocketAddress(InetAddress.getByName(ip), port));
            }
        } catch (IOException ex) {
            return;
        }
        super.start();
    }

    @Override
    public void run() {
        try (IdentServer server = this) {
            while (!isInterrupted()) {
                handleNextConnection(server);
            }
        } catch (IOException e) {
        }
    }

    private void handleNextConnection(IdentServer server) {
        try (Socket socket = server.getServerSocket().accept()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), encoding));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), encoding));
            InetSocketAddress remoteAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
            String line = reader.readLine();
            if (StringUtils.isBlank(line)) {
                socket.close();
                return;
            }
            String[] parsedLine = StringUtils.split(line, ", ");
            if (parsedLine.length != 2) {
                socket.close();
                return;
            }
            int localPort = Utils.tryParseInt(parsedLine[0], -1);
            int remotePort = Utils.tryParseInt(parsedLine[1], -1);
            if (localPort == -1 || remotePort == -1) {
                socket.close();
                return;
            }
            IdentEntry identEntry = null;
            synchronized (identEntries) {
                for (IdentEntry curIdentEntry : identEntries) {
                    if (curIdentEntry.getRemoteAddress().equals(remoteAddress.getAddress())
                            && curIdentEntry.getRemotePort() == remotePort
                            && curIdentEntry.getLocalPort() == localPort) {
                        identEntry = curIdentEntry;
                        break;
                    }
                }
            }
            if (identEntry == null) {
                String response = localPort + ", " + remotePort + " ERROR : NO-USER";
                writer.write(response + "\r\n");
                writer.flush();
                socket.close();
                return;
            }
            String response = line + " : USERID : UNIX : " + identEntry.getLogin();
            writer.write(response + "\r\n");
            writer.flush();
        } catch (IOException e) {
        }
    }

    protected void addIdentEntry(InetAddress remoteAddress, int remotePort, int localPort, String login) {
        synchronized (identEntries) {
            identEntries.add(new IdentEntry(remoteAddress, remotePort, localPort, login));
        }
    }

    @Override
    public void close() throws IOException {
        serverSocket.close();
        identEntries.clear();
        interrupt();
    }
}
