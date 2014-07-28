package org.pircbotz.hooks.events;

import org.pircbotz.PircBotZ;
import org.pircbotz.UserChannelDao;
import org.pircbotz.hooks.Event;
import org.pircbotz.generics.GenericChannelUserEvent;
import org.pircbotz.snapshot.ChannelSnapshot;
import org.pircbotz.snapshot.UserSnapshot;

public class PartEvent extends Event implements GenericChannelUserEvent {

    private final UserChannelDao<PircBotZ, UserSnapshot, ChannelSnapshot> daoSnapshot;
    private final ChannelSnapshot channel;
    private final UserSnapshot user;
    private final String reason;

    public PartEvent(PircBotZ bot, UserChannelDao<PircBotZ, UserSnapshot, ChannelSnapshot> daoSnapshot, ChannelSnapshot channel, UserSnapshot user, String reason) {
        super(bot);
        this.daoSnapshot = daoSnapshot;
        this.channel = channel;
        this.user = user;
        this.reason = reason;
    }

    @Override
    public void respond(String response) {
        getChannel().send().message(response);
    }

    public UserChannelDao<PircBotZ, UserSnapshot, ChannelSnapshot> getDaoSnapshot() {
        return daoSnapshot;
    }

    @Override
    public ChannelSnapshot getChannel() {
        return channel;
    }

    @Override
    public UserSnapshot getUser() {
        return user;
    }

    public String getReason() {
        return reason;
    }
}
