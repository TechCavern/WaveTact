package org.pircbotz.hooks.events;

import org.pircbotz.PircBotZ;
import org.pircbotz.User;
import org.pircbotz.hooks.Event;
import org.pircbotz.generics.GenericUserEvent;

public class NickChangeEvent extends Event implements GenericUserEvent {

    private final String oldNick;
    private final String newNick;
    private final User user;

    public NickChangeEvent(PircBotZ bot, String oldNick, String newNick, User user) {
        super(bot);
        this.oldNick = oldNick;
        this.newNick = newNick;
        this.user = user;
    }

    @Override
    public void respond(String response) {
        getUser().send().message(response);
    }

    public String getOldNick() {
        return oldNick;
    }

    public String getNewNick() {
        return newNick;
    }

    public User getUser() {
        return user;
    }
}
