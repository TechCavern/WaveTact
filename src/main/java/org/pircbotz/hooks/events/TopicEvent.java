package org.pircbotz.hooks.events;

import org.pircbotz.Channel;
import org.pircbotz.PircBotZ;
import org.pircbotz.User;
import org.pircbotz.hooks.Event;
import org.pircbotz.generics.GenericChannelUserEvent;

public class TopicEvent extends Event implements GenericChannelUserEvent {

    private final Channel channel;
    private final User user;

    public TopicEvent(PircBotZ bot, Channel channel, String oldTopic, String topic, User user, long date, boolean changed) {
        super(bot);
        this.channel = channel;
        String oldTopic1 = oldTopic;
        String topic1 = topic;
        this.user = user;
        boolean changed1 = changed;
        long date1 = date;
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
}
