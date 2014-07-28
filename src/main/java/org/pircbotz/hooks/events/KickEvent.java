package org.pircbotz.hooks.events;

import org.pircbotz.Channel;
import org.pircbotz.PircBotZ;
import org.pircbotz.User;
import org.pircbotz.hooks.Event;
import org.pircbotz.generics.GenericChannelUserEvent;

public class KickEvent extends Event implements GenericChannelUserEvent {

    private final Channel channel;
    private final User user;
    private final User recipient;
    private final String reason;

    public KickEvent(PircBotZ bot, Channel channel, User user, User recipient, String reason) {
        super(bot);
        this.channel = channel;
        this.user = user;
        this.recipient = recipient;
        this.reason = reason;
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

    public User getRecipient() {
        return recipient;
    }

    public String getReason() {
        return reason;
    }
}
