package org.pircbotz.managers;

import java.util.Set;
import org.pircbotz.PircBotZ;
import org.pircbotz.hooks.Event;
import org.pircbotz.hooks.Listener;

public interface ListenerManager {

    public void dispatchEvent(Event event);

    public boolean addListener(Listener listener);

    public boolean removeListener(Listener listener);

    public boolean listenerExists(Listener listener);

    public Set<Listener> getListeners();

    public void setCurrentId(long currentId);

    public long getCurrentId();

    public long incrementCurrentId();

    public void shutdown(PircBotZ bot);
}
