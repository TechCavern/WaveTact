package info.techcavern.wavetact.objects;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class CommandIO {
    private final InputStream is;
    private final OutputStream os;
    private final PrintStream ps;

    public CommandIO(InputStream is, OutputStream os) {
        this.is = is;
        this.os = os;
        this.ps = new PrintStream(os, true);
    }

    public OutputStream getOutputStream() {
        return os;
    }

    public InputStream getInputStream() {
        return is;
    }

    public PrintStream getPrintStream() {
        return ps;
    }
}
