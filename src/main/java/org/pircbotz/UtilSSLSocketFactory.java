package org.pircbotz;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.LinkedList;
import java.util.List;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class UtilSSLSocketFactory extends SSLSocketFactory {

    private SSLSocketFactory wrappedFactory;
    private boolean trustingAllCertificates = false;
    private boolean diffieHellmanDisabled = false;

    public UtilSSLSocketFactory() {
        wrappedFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
    }

    public UtilSSLSocketFactory(SSLSocketFactory providedFactory) {
        wrappedFactory = providedFactory;
    }

    public UtilSSLSocketFactory trustAllCertificates() throws CertificateException {
        if (trustingAllCertificates) {
            return this;
        }
        trustingAllCertificates = true;
        try {
            TrustManager[] tm = new TrustManager[]{new TrustingX509TrustManager()};
            SSLContext context = SSLContext.getInstance("SSL");
            context.init(new KeyManager[0], tm, new SecureRandom());
            wrappedFactory = context.getSocketFactory();
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            throw new CertificateException("Can't recreate socket factory that trusts all certificates", e);
        }
        return this;
    }

    protected UtilSSLSocketFactory disableDiffieHellman() {
        diffieHellmanDisabled = true;
        return this;
    }

    Socket prepare(Socket socket) {
        SSLSocket sslSocket = (SSLSocket) socket;
        if (diffieHellmanDisabled) {
            List<String> limited = new LinkedList<>();
            for (String suite : sslSocket.getEnabledCipherSuites()) {
                if (!suite.contains("_DHE_")) {
                    limited.add(suite);
                }
            }
            sslSocket.setEnabledCipherSuites(limited.toArray(new String[limited.size()]));
        }
        return sslSocket;
    }

    @Override
    public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
        return prepare(wrappedFactory.createSocket(host, port));
    }

    @Override
    public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException, UnknownHostException {
        return prepare(wrappedFactory.createSocket(host, port, localHost, localPort));
    }

    @Override
    public Socket createSocket(InetAddress address, int port) throws IOException {
        return prepare(wrappedFactory.createSocket(address, port));
    }

    @Override
    public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
        return prepare(wrappedFactory.createSocket(address, port, localAddress, localPort));
    }

    @Override
    public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
        return prepare(wrappedFactory.createSocket(s, host, port, autoClose));
    }

    @Override
    public String[] getDefaultCipherSuites() {
        return wrappedFactory.getDefaultCipherSuites();
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return wrappedFactory.getSupportedCipherSuites();
    }

    private static class TrustingX509TrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] cert, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] cert, String authType) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }
}
