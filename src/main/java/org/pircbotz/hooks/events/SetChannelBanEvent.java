package org.pircbotz.hooks.events;

import org.pircbotz.Channel;
import org.pircbotz.PircBotZ;
import org.pircbotz.User;
import org.pircbotz.hooks.Event;
import org.pircbotz.generics.GenericChannelModeEvent;

public class SetChannelBanEvent extends Event implements GenericChannelModeEvent {

    private final Channel channel;
    private final User user;
    private final String hostmask;

    public SetChannelBanEvent(PircBotZ bot, Channel channel, User user, String hostmask) {
        super(bot);
        this.channel = channel;
        this.user = user;
        this.hostmask = hostmask;
    }

    @Override
    public void respond(String response) {
        getChannel().send().message(getUser(), response);
    }

    @Override
    public Channel getChannel() {
        return channel;
    }

    @Override
    public User getUser() {
        return user;
    }

    public String getHostmask() {
        return hostmask;
    }
}
