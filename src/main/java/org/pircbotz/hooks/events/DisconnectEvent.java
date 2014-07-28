package org.pircbotz.hooks.events;

import org.pircbotz.Channel;
import org.pircbotz.PircBotZ;
import org.pircbotz.User;
import org.pircbotz.UserChannelDao;
import org.pircbotz.hooks.Event;

public class DisconnectEvent extends Event {

    private final UserChannelDao<PircBotZ, ? extends User, ? extends Channel> daoSnapshot;
    private final Exception disconnectException;

    public DisconnectEvent(PircBotZ bot, UserChannelDao<PircBotZ, ? extends User, ? extends Channel> daoSnapshot, Exception disconnectException) {
        super(bot);
        this.daoSnapshot = daoSnapshot;
        this.disconnectException = disconnectException;
    }

    @Override
    @Deprecated
    public void respond(String response) {
        throw new UnsupportedOperationException("Attepting to respond to a disconnected server");
    }
}
