package org.pircbotz.hooks.events;

import org.pircbotz.PircBotZ;
import org.pircbotz.hooks.Event;

public class ServerPingEvent extends Event {

    private final String response;

    public ServerPingEvent(PircBotZ bot, String response) {
        super(bot);
        this.response = response;
    }

    @Override
    public void respond(String response) {
        getBot().sendRaw().rawLine(response);
    }

    public String getResponse() {
        return response;
    }
}
