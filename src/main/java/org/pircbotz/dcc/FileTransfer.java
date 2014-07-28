package org.pircbotz.dcc;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import org.pircbotz.Configuration;
import org.pircbotz.PircBotZ;
import org.pircbotz.User;

public abstract class FileTransfer {

    private final Configuration<PircBotZ> configuration;
    private final Socket socket;
    private final User user;
    private final File file;
    private final long startPosition;
    private DccState state = DccState.INIT;
    private final Object stateLock = new Object();

    public FileTransfer(Configuration<PircBotZ> configuration, Socket socket, User user, File file, long startPosition) {
        this.configuration = configuration;
        this.socket = socket;
        this.user = user;
        this.file = file;
        this.startPosition = startPosition;
    }

    public void transfer() throws IOException {
        if (state != DccState.INIT) {
            synchronized (stateLock) {
                if (state != DccState.INIT) {
                    throw new IOException("Cannot receive file twice (Current state: " + state + ")");
                }
            }
        }
        state = DccState.RUNNING;
        transferFile();
        state = DccState.DONE;
    }

    protected abstract void transferFile() throws IOException;

    protected void onAfterSend() {
    }

    public boolean isFinished() {
        return state == DccState.DONE;
    }

    public Configuration<PircBotZ> getConfiguration() {
        return configuration;
    }

    public Socket getSocket() {
        return socket;
    }

    public User getUser() {
        return user;
    }

    public File getFile() {
        return file;
    }

    public long getStartPosition() {
        return startPosition;
    }

    public abstract long getBytesTransfered();

    public DccState getState() {
        return state;
    }

    public Object getStateLock() {
        return stateLock;
    }
}
