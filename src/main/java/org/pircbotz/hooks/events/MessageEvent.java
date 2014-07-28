package org.pircbotz.hooks.events;

import org.pircbotz.Channel;
import org.pircbotz.PircBotZ;
import org.pircbotz.User;
import org.pircbotz.hooks.Event;
import org.pircbotz.generics.GenericChannelUserEvent;
import org.pircbotz.generics.GenericMessageEvent;

public class MessageEvent extends Event implements GenericMessageEvent, GenericChannelUserEvent {

    private final Channel channel;
    private final User user;
    private final String message;

    public MessageEvent(PircBotZ bot, Channel channel, User user, String message) {
        super(bot);
        this.channel = channel;
        this.user = user;
        this.message = message;
    }

    @Override
    public void respond(String response) {
        getChannel().send().message(getUser(), response);
    }

    public Channel getChannel() {
        return channel;
    }

    public User getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }
}
