package org.pircbotz.hooks.events;

import org.pircbotz.PircBotZ;
import org.pircbotz.hooks.Event;

public class UnknownEvent extends Event {

    private final String line;

    public UnknownEvent(PircBotZ bot, String line) {
        super(bot);
        this.line = line;
    }

    @Override
    public void respond(String response) {
        getBot().sendRaw().rawLine(response);
    }

    public String getLine() {
        return line;
    }
}
