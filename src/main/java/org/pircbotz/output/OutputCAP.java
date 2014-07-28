package org.pircbotz.output;

import org.pircbotz.PircBotZ;
import org.apache.commons.lang3.StringUtils;

public class OutputCAP {

    private final PircBotZ bot;

    public OutputCAP(PircBotZ bot) {
        this.bot = bot;
    }

    public void requestSupported() {
        bot.sendRaw().rawLineNow("CAP LS");
    }

    public void requestEnabled() {
        bot.sendRaw().rawLineNow("CAP LIST");
    }

    public void request(String... capability) {
        bot.sendRaw().rawLineNow("CAP REQ :" + StringUtils.join(capability, " "));
    }

    public void clear() {
        bot.sendRaw().rawLineNow("CAP CLEAR");
    }

    public void end() {
        bot.sendRaw().rawLineNow("CAP END");
    }
}
