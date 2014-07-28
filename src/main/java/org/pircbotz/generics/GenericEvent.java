package org.pircbotz.generics;

import org.pircbotz.PircBotZ;
import org.pircbotz.hooks.Event;

public interface GenericEvent extends Comparable<Event> {

    public void respond(String response);

    public PircBotZ getBot();

    public long getTimestamp();
}
