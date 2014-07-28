package org.pircbotz.hooks.events;

import java.util.List;
import org.pircbotz.PircBotZ;
import org.pircbotz.hooks.Event;

public class ServerResponseEvent extends Event {

    private final int code;
    private final String rawLine;
    private final List<String> parsedResponse;

    public ServerResponseEvent(PircBotZ bot, int code, String rawLine, List<String> parsedResponse) {
        super(bot);
        this.code = code;
        this.rawLine = rawLine;
        this.parsedResponse = parsedResponse;
    }

    @Override
    public void respond(String response) {
        getBot().sendRaw().rawLine(response);
    }

    public int getCode() {
        return code;
    }

    public String getRawLine() {
        return rawLine;
    }

    public List<String> getParsedResponse() {
        return parsedResponse;
    }
}
