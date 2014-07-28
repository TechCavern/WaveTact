package org.pircbotz.hooks.events;

import java.util.Set;
import org.pircbotz.Channel;
import org.pircbotz.PircBotZ;
import org.pircbotz.User;
import org.pircbotz.hooks.Event;
import org.pircbotz.generics.GenericChannelEvent;

public class UserListEvent extends Event implements GenericChannelEvent {

    private final Channel channel;
    private final Set<User> users;

    public UserListEvent(PircBotZ bot, Channel channel, Set<User> users) {
        super(bot);
        this.channel = channel;
        this.users = users;
    }

    @Override
    public void respond(String response) {
        getChannel().send().message(response);
    }

    @Override
    public Channel getChannel() {
        return channel;
    }

    public Set<User> getUsers() {
        return users;
    }
}
