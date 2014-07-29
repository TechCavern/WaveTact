package org.pircbotz.hooks.events;

import org.pircbotz.PircBotZ;
import org.pircbotz.hooks.Event;

public class InviteEvent extends Event {

    private final String user;
    private final String channel;

    public InviteEvent(PircBotZ bot, String user, String channel) {
        super(bot);
        this.user = user;
        this.channel = channel;
    }

    @Override
    public void respond(String response) {
        getBot().sendIRC().message(getUser(), response);
    }

    public String getUser() {
        return user;
    }

    public String getChannel() {
        return channel;
    }
}
