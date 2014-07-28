package org.pircbotz.hooks.events;

import org.pircbotz.Channel;
import org.pircbotz.PircBotZ;
import org.pircbotz.User;
import org.pircbotz.hooks.Event;
import org.pircbotz.generics.GenericChannelUserEvent;
import org.pircbotz.generics.GenericMessageEvent;

public class NoticeEvent extends Event implements GenericMessageEvent, GenericChannelUserEvent {

    private final User user;
    private final Channel channel;
    private final String notice;

    public NoticeEvent(PircBotZ bot, User user, Channel channel, String notice) {
        super(bot);
        this.user = user;
        this.channel = channel;
        this.notice = notice;
    }

    @Override
    public String getMessage() {
        return notice;
    }

    @Override
    public void respond(String response) {
        if (getChannel() == null) {
            getUser().send().message(response);
        } else {
            getChannel().send().message(getUser(), response);
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

    public String getNotice() {
        return notice;
    }
}
