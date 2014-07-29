package org.pircbotz.hooks.events;

import org.pircbotz.Channel;
import org.pircbotz.PircBotZ;
import org.pircbotz.User;
import org.pircbotz.hooks.Event;
import org.pircbotz.generics.GenericChannelUserEvent;

public class TopicEvent extends Event implements GenericChannelUserEvent {

    private final Channel channel;
    private final String oldTopic;
    private final String topic;
    private final User user;
    private final boolean changed;
    private final long date;

    public TopicEvent(PircBotZ bot, Channel channel, String oldTopic, String topic, User user, long date, boolean changed) {
        super(bot);
        this.channel = channel;
        this.oldTopic = oldTopic;
        this.topic = topic;
        this.user = user;
        this.changed = changed;
        this.date = date;
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
