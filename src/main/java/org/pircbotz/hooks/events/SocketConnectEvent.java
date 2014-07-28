package org.pircbotz.hooks.events;

import org.pircbotz.PircBotZ;
import org.pircbotz.hooks.Event;

public class SocketConnectEvent extends Event {

    public SocketConnectEvent(PircBotZ bot) {
        super(bot);
    }

    @Override
    public void respond(String response) {
        getBot().sendRaw().rawLine(response);
    }
}
