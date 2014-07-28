package org.pircbotz.hooks.events;

import org.pircbotz.Channel;
import org.pircbotz.PircBotZ;
import org.pircbotz.User;
import org.pircbotz.hooks.Event;
import org.pircbotz.generics.GenericCTCPEvent;
import org.pircbotz.generics.GenericChannelEvent;

public class VersionEvent extends Event implements GenericCTCPEvent, GenericChannelEvent {

    private final User user;
    private final Channel channel;

    public VersionEvent(PircBotZ bot, User user, Channel channel) {
        super(bot);
        this.user = user;
        this.channel = channel;
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
