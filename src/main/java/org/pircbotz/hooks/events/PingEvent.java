package org.pircbotz.hooks.events;

import org.pircbotz.Channel;
import org.pircbotz.PircBotZ;
import org.pircbotz.User;
import org.pircbotz.hooks.Event;
import org.pircbotz.generics.GenericCTCPEvent;

public class PingEvent extends Event implements GenericCTCPEvent {

    private final User user;
    private final Channel channel;
    private final String pingValue;

    public PingEvent(PircBotZ bot, User user, Channel channel, String pingValue) {
        super(bot);
        this.user = user;
        this.channel = channel;
        this.pingValue = pingValue;
    }

    @Override
    public void respond(String response) {
        getUser().send().ctcpResponse(response);
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public Channel getChannel() {
        return channel;
    }

    public String getPingValue() {
        return pingValue;
    }
}
