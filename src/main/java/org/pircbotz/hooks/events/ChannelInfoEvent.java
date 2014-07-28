package org.pircbotz.hooks.events;

import java.util.List;
import org.pircbotz.ChannelListEntry;
import org.pircbotz.PircBotZ;
import org.pircbotz.hooks.Event;

public class ChannelInfoEvent extends Event {

    private final List<ChannelListEntry> list;

    public ChannelInfoEvent(PircBotZ bot, List<ChannelListEntry> list) {
        super(bot);
        this.list = list;
    }

    @Override
    public void respond(String response) {
        getBot().sendRaw().rawLine(response);
    }

    public List<ChannelListEntry> getList() {
        return list;
    }
}
