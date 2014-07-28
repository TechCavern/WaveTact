package org.pircbotz.hooks;

import org.pircbotz.hooks.events.ServerPingEvent;

public class CoreHooks extends ListenerAdapter {

    @Override
    public void onServerPing(ServerPingEvent event) {
        event.getBot().sendRaw().rawLine("PONG " + event.getResponse());
    }
}
