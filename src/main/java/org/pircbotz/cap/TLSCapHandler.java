package org.pircbotz.cap;

import java.util.List;
import javax.net.ssl.SSLSocketFactory;
import org.pircbotz.PircBotZ;
import org.pircbotz.exception.CAPException;

public class TLSCapHandler extends EnableCapHandler {

    private SSLSocketFactory sslSocketFactory;

    public TLSCapHandler() {
        super("tls");
        this.sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
    }

    public TLSCapHandler(SSLSocketFactory sslSocketFactory, boolean ignoreFail) {
        super("tls", ignoreFail);
        this.sslSocketFactory = sslSocketFactory;
    }

    @Override
    public boolean handleACK(PircBotZ bot, List<String> capabilities) throws CAPException {
        if (capabilities.contains("tls")) {
            bot.sendRaw().rawLineNow("STARTTLS");
        }
        return false;
    }

    @Override
    public boolean handleUnknown(PircBotZ bot, String rawLine) {
        return rawLine.contains(" 670 ");
    }

    public SSLSocketFactory getSslSocketFactory() {
        return sslSocketFactory;
    }
}
