package org.pircbotz.dcc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.Charset;
import org.pircbotz.PircBotZ;
import org.pircbotz.User;
import org.pircbotz.exception.DccException;
import org.apache.commons.lang3.Validate;

public class Chat {

    private final User user;
    private final BufferedReader bufferedReader;
    private final BufferedWriter bufferedWriter;
    private final Socket socket;
    private boolean finished;

    protected Chat(User user, Socket socket, Charset encoding) throws IOException {
        Validate.notNull(user, "User cannot be null");
        Validate.notNull(socket, "Socket cannot be null");
        Validate.notNull(encoding, "Encoding cannot be null");
        this.user = user;
        this.socket = socket;
        this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), encoding));
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), encoding));
    }

    public String readLine() throws IOException {
        if (finished) {
            throw new DccException(DccException.Reason.ChatNotConnected, user, "Chat has already finished");
        }
        String line = bufferedReader.readLine();
        return line;
    }

    public void sendLine(String line) throws IOException {
        Validate.notNull(line, "Line cannot be null");
        if (finished) {
            throw new DccException(DccException.Reason.ChatNotConnected, user, "Chat has already finished");
        }
        synchronized (bufferedWriter) {
            bufferedWriter.write(line + "\r\n");
            bufferedWriter.flush();
        }
    }

    public void close() throws IOException {
        if (finished) {
            throw new DccException(DccException.Reason.ChatNotConnected, user, "Chat has already finished");
        }
        finished = true;
        socket.close();
    }

    public User getUser() {
        return user;
    }

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    public BufferedWriter getBufferedWriter() {
        return bufferedWriter;
    }

    public Socket getSocket() {
        return socket;
    }

    public boolean isFinished() {
        return finished;
    }
}
