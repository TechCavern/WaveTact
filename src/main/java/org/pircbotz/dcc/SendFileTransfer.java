package org.pircbotz.dcc;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import org.pircbotz.Configuration;
import org.pircbotz.PircBotZ;
import org.pircbotz.User;

public class SendFileTransfer extends FileTransfer {

    private long bytesTransfered;

    public SendFileTransfer(Configuration<PircBotZ> configuration, Socket socket, User user, File file, long startPosition) {
        super(configuration, socket, user, file, startPosition);
    }

    @Override
    protected void transferFile() throws IOException {
        try (BufferedOutputStream socketOutput = new BufferedOutputStream(getSocket().getOutputStream())) {
            try (BufferedInputStream socketInput = new BufferedInputStream(getSocket().getInputStream())) {
                try (BufferedInputStream fileInput = new BufferedInputStream(new FileInputStream(getFile()))) {
                    if (getStartPosition() > 0) {
                        long bytesSkipped = 0;
                        while (bytesSkipped < getStartPosition()) {
                            bytesSkipped += fileInput.skip(getStartPosition() - bytesSkipped);
                        }
                    }
                    byte[] outBuffer = new byte[getConfiguration().getDccTransferBufferSize()];
                    byte[] inBuffer = new byte[4];
                    int bytesRead;
                    while ((bytesRead = fileInput.read(outBuffer, 0, outBuffer.length)) != -1) {
                        socketOutput.write(outBuffer, 0, bytesRead);
                        socketOutput.flush();
                        socketInput.read(inBuffer, 0, inBuffer.length);
                        bytesTransfered += bytesRead;
                        onAfterSend();
                    }
                }
            }
        }
    }

    @Override
    public long getBytesTransfered() {
        return bytesTransfered;
    }
}
