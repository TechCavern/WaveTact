package org.pircbotz.hooks.events;

import java.util.List;
import org.pircbotz.Channel;
import org.pircbotz.PircBotZ;
import org.pircbotz.User;
import org.pircbotz.hooks.Event;
import org.pircbotz.generics.GenericChannelModeEvent;

public class ModeEvent extends Event implements GenericChannelModeEvent {

    private final Channel channel;
    private final User user;
    private final String mode;
    private final List<String> modeParsed;

    public ModeEvent(PircBotZ bot, Channel channel, User user, String mode, List<String> modeParsed) {
        super(bot);
        this.channel = channel;
        this.user = user;
        this.mode = mode;
        this.modeParsed = modeParsed;
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

    public String getMode() {
        return mode;
    }

    public List<String> getModeParsed() {
        return modeParsed;
    }
}
