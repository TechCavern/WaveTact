package org.pircbotz.hooks.events;

import org.pircbotz.Channel;
import org.pircbotz.PircBotZ;
import org.pircbotz.User;
import org.pircbotz.hooks.Event;
import org.pircbotz.generics.GenericUserModeEvent;

public class OwnerEvent extends Event implements GenericUserModeEvent {

    private final Channel channel;
    private final User user;
    private final User recipient;
    private final boolean isOwner;

    public OwnerEvent(PircBotZ bot, Channel channel, User user, User recipient, boolean isOwner) {
        super(bot);
        this.channel = channel;
        this.user = user;
        this.recipient = recipient;
        this.isOwner = isOwner;
    }

    @Deprecated
    public boolean isFounder() {
        return isOwner;
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

    public boolean isIsOwner() {
        return isOwner;
    }
}
