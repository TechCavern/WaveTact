package org.pircbotz.hooks.events;

import org.pircbotz.PircBotZ;
import org.pircbotz.hooks.Event;
import org.pircbotz.generics.GenericUserEvent;
import org.pircbotz.snapshot.UserChannelDaoSnapshot;
import org.pircbotz.snapshot.UserSnapshot;

public class QuitEvent extends Event implements GenericUserEvent {

    private final UserChannelDaoSnapshot<PircBotZ> daoSnapshot;
    private final UserSnapshot user;
    private final String reason;

    public QuitEvent(PircBotZ bot, UserChannelDaoSnapshot<PircBotZ> daoSnapshot, UserSnapshot user, String reason) {
        super(bot);
        this.daoSnapshot = daoSnapshot;
        this.user = user;
        this.reason = reason;
        user.setDao(daoSnapshot);
    }

    @Override
    public void respond(String response) {
        throw new UnsupportedOperationException("Attempting to respond to a user that quit");
    }

    public UserChannelDaoSnapshot<PircBotZ> getDaoSnapshot() {
        return daoSnapshot;
    }

    @Override
    public UserSnapshot getUser() {
        return user;
    }

    public String getReason() {
        return reason;
    }
}
