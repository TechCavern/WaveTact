package org.pircbotz.hooks;

import com.google.common.collect.ComparisonChain;
import org.pircbotz.PircBotZ;
import org.pircbotz.managers.ListenerManager;
import org.pircbotz.generics.GenericEvent;

public abstract class Event implements GenericEvent {

    private final long timestamp;
    private final PircBotZ bot;
    private final long id;

    public Event(PircBotZ bot) {
        this(bot, bot.getConfiguration().getListenerManager());
    }

    public Event(ListenerManager listenerManager) {
        this(null, listenerManager);
    }

    public Event(PircBotZ bot, ListenerManager listenerManager) {
        this.timestamp = System.currentTimeMillis();
        this.bot = bot;
        this.id = listenerManager.incrementCurrentId();
    }

    @Override
    public PircBotZ getBot() {
        return bot;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    public long getId() {
        return id;
    }

    @Override
    public int compareTo(Event other) {
        ComparisonChain comparison = ComparisonChain.start()
                .compare(getTimestamp(), other.getTimestamp())
                .compare(getId(), other.getId());
        if (bot != null && other.getBot() != null) {
            comparison.compare(bot.getBotId(), other.getBot().getBotId());
        }
        return comparison.result();
    }
}
