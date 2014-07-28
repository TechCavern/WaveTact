package org.pircbotz.hooks.events;

import org.pircbotz.Channel;
import org.pircbotz.PircBotZ;
import org.pircbotz.User;
import org.pircbotz.hooks.Event;
import org.pircbotz.generics.GenericChannelUserEvent;
import org.pircbotz.generics.GenericMessageEvent;

public class ActionEvent extends Event implements GenericMessageEvent, GenericChannelUserEvent {

    private final User user;
    private final Channel channel;
    private final String action;

    public ActionEvent(PircBotZ bot, User user, Channel channel, String action) {
        super(bot);
        this.user = user;
        this.channel = channel;
        this.action = action;
    }

    @Override
    public String getMessage() {
        return action;
    }

    @Override
    public void respond(String response) {
        if (getChannel() == null) {
            getUser().send().action(response);
        } else {
            getChannel().send().action(response);
        }
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public Channel getChannel() {
        return channel;
    }
}
