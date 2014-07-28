package org.pircbotz.cap;

import java.util.List;

import org.pircbotz.PircBotZ;
import org.pircbotz.exception.CAPException;

public class EnableCapHandler implements CapHandler {

    private final String cap;
    private final boolean ignoreFail;

    EnableCapHandler(String cap) {
        this.cap = cap;
        this.ignoreFail = false;
    }

    public EnableCapHandler(String cap, boolean ignoreFail) {
        this.cap = cap;
        this.ignoreFail = ignoreFail;
    }

    @Override
    public boolean handleLS(PircBotZ bot, List<String> capabilities) throws CAPException {
        if (capabilities.contains(cap)) {
            bot.sendCAP().request(cap);
        } else if (!ignoreFail) {
            throw new CAPException(CAPException.Reason.UnsupportedCapability, cap);
        } else {
            return true;
        }
        return false;
    }

    @Override
    public boolean handleACK(PircBotZ bot, List<String> capabilities) throws CAPException {
        return capabilities.contains(cap);
    }

    @Override
    public boolean handleNAK(PircBotZ bot, List<String> capabilities) throws CAPException {
        if (capabilities.contains(cap)) {
            bot.getEnabledCapabilities().remove(cap);
            if (!ignoreFail) {
                throw new CAPException(CAPException.Reason.UnsupportedCapability, cap);
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean handleUnknown(PircBotZ bot, String rawLine) {
        return false;
    }

    public String getCap() {
        return cap;
    }
}
