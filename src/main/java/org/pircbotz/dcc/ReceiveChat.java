package org.pircbotz.dcc;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.Charset;
import org.pircbotz.User;

public class ReceiveChat extends Chat {

    public ReceiveChat(User user, Socket socket, Charset encoding) throws IOException {
        super(user, socket, encoding);
    }
}
