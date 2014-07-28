package org.pircbotz.hooks.events;

import org.pircbotz.Channel;
import org.pircbotz.PircBotZ;
import org.pircbotz.User;
import org.pircbotz.UserChannelDao;
import org.pircbotz.hooks.Event;

public class DisconnectEvent extends Event {

    public DisconnectEvent(PircBotZ bot, UserChannelDao<PircBotZ, ? extends User, ? extends Channel> daoSnapshot, Exception disconnectException) {
        super(bot);
        UserChannelDao<PircBotZ, ? extends User, ? extends Channel> daoSnapshot1 = daoSnapshot;
        Exception disconnectException1 = disconnectException;
    }

    @Override
    @Deprecated
    public void respond(String response) {
        throw new UnsupportedOperationException("Attepting to respond to a disconnected server");
    }
}
