package org.pircbotz.hooks.events;

import org.pircbotz.Channel;
import org.pircbotz.PircBotZ;
import org.pircbotz.User;
import org.pircbotz.hooks.Event;
import org.pircbotz.generics.GenericCTCPEvent;

public class TimeEvent extends Event implements GenericCTCPEvent {

    private final Channel channel;
    private final User user;

    public TimeEvent(PircBotZ bot, Channel channel, User user) {
        super(bot);
        this.channel = channel;
        this.user = user;
    }

    @Override
    public void respond(String response) {
        getUser().send().ctcpResponse(response);
    }

    @Override
    public Channel getChannel() {
        return channel;
    }

    @Override
    public User getUser() {
        return user;
    }
}
