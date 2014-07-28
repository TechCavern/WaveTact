package org.pircbotz;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.net.SocketFactory;

public class ProxySocketFactory extends SocketFactory {

    private final Proxy proxy;

    public ProxySocketFactory(Proxy proxy) {
        this.proxy = proxy;
    }

    public ProxySocketFactory(Proxy.Type proxyType, String hostname, int port) {
        this.proxy = new Proxy(proxyType, new InetSocketAddress(hostname, port));
    }

    @Override
    public Socket createSocket(String string, int i) throws IOException, UnknownHostException {
        Socket socket = new Socket(proxy);
        socket.connect(new InetSocketAddress(string, i));
        return socket;
    }

    @Override
    public Socket createSocket(String string, int i, InetAddress localAddress, int localPort) throws IOException, UnknownHostException {
        Socket socket = new Socket(proxy);
        socket.bind(new InetSocketAddress(localAddress, localPort));
        socket.connect(new InetSocketAddress(string, i));
        return socket;
    }

    @Override
    public Socket createSocket(InetAddress ia, int i) throws IOException {
        Socket socket = new Socket(proxy);
        socket.connect(new InetSocketAddress(ia, i));
        return socket;
    }

    @Override
    public Socket createSocket(InetAddress ia, int i, InetAddress localAddress, int localPort) throws IOException {
        Socket socket = new Socket(proxy);
        socket.bind(new InetSocketAddress(localAddress, localPort));
        socket.connect(new InetSocketAddress(ia, i));
        return socket;
    }
}
