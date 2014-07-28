package org.pircbotz;

import java.net.InetAddress;

public class IdentEntry {

    private final InetAddress remoteAddress;
    private final int remotePort;
    private final int localPort;
    private final String login;

    public IdentEntry(InetAddress remoteAddress, int remotePort, int localPort, String login) {
        this.remoteAddress = remoteAddress;
        this.remotePort = remotePort;
        this.localPort = localPort;
        this.login = login;
    }

    public InetAddress getRemoteAddress() {
        return remoteAddress;
    }

    public int getRemotePort() {
        return remotePort;
    }

    public int getLocalPort() {
        return localPort;
    }

    public String getLogin() {
        return login;
    }
}
