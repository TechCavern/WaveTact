package org.pircbotz.cap;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import org.pircbotz.PircBotZ;
import org.pircbotz.exception.CAPException;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.binary.Base64;

public class SASLCapHandler implements CapHandler {

    private final String username;
    private final String password;
    private final boolean ignoreFail;

    public SASLCapHandler(String username, String password) {
        this.username = username;
        this.password = password;
        this.ignoreFail = false;
    }

    @Override
    public boolean handleLS(PircBotZ bot, List<String> capabilities) throws CAPException {
        if (capabilities.contains("sasl")) {
            bot.sendCAP().request("sasl");
        } else {
            throw new CAPException(CAPException.Reason.UnsupportedCapability, "SASL");
        }
        return false;
    }

    @Override
    public boolean handleACK(PircBotZ bot, List<String> capabilities) {
        if (capabilities.contains("sasl")) {
            bot.sendRaw().rawLineNow("AUTHENTICATE PLAIN");
        }
        return false;
    }

    @Override
    public boolean handleUnknown(PircBotZ bot, String rawLine) throws CAPException {
        try {
            if (rawLine.equals("AUTHENTICATE +")) {
                String encodedAuth = Base64.encodeBase64String((username + '\0' + username + '\0' + password).getBytes(CharEncoding.UTF_8));
                bot.sendRaw().rawLineNow("AUTHENTICATE " + encodedAuth);
            }
        }catch(IOException e){

        }
        String[] parsedLine = rawLine.split(" ", 4);
        if (parsedLine.length >= 1) {
            switch (parsedLine[1]) {
                case "904":
                case "905":
                    bot.getEnabledCapabilities().remove("sasl");
                    if (!ignoreFail) {
                        throw new CAPException(CAPException.Reason.SASLFailed, "SASL Authentication failed with message: " + parsedLine[3].substring(1));
                    }
                    return true;
                case "900":
                case "903":
                    return true;
            }
        }
        return false;
    }

    @Override
    public boolean handleNAK(PircBotZ bot, List<String> capabilities) throws CAPException {
        if (!ignoreFail && capabilities.contains("sasl")) {
            bot.getEnabledCapabilities().remove("sasl");
            throw new CAPException(CAPException.Reason.UnsupportedCapability, "SASL");
        }
        return false;
    }
}
