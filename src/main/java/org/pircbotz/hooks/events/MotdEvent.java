package org.pircbotz.hooks.events;

import org.pircbotz.PircBotZ;
import org.pircbotz.hooks.Event;

public class MotdEvent extends Event {

    private final String motd;

    public MotdEvent(PircBotZ bot, String motd) {
        super(bot);
        this.motd = motd;
    }

    @Override
    public void respond(String response) {
        getBot().sendRaw().rawLine(response);
    }

    public String getMotd() {
        return motd;
    }
}
