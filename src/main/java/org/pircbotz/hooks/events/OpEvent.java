package org.pircbotz.hooks.events;

import org.pircbotz.Channel;
import org.pircbotz.PircBotZ;
import org.pircbotz.User;
import org.pircbotz.hooks.Event;
import org.pircbotz.generics.GenericUserModeEvent;

public class OpEvent extends Event implements GenericUserModeEvent {

    private final Channel channel;
    private final User user;
    private final User recipient;
    private final boolean isOp;

    public OpEvent(PircBotZ bot, Channel channel, User user, User recipient, boolean isOp) {
        super(bot);
        this.channel = channel;
        this.user = user;
        this.recipient = recipient;
        this.isOp = isOp;
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

    @Override
    public User getRecipient() {
        return recipient;
    }

    public boolean isIsOp() {
        return isOp;
    }
}
