package org.pircbotz.hooks.events;

import org.pircbotz.PircBotZ;
import org.pircbotz.User;
import org.pircbotz.hooks.Event;
import org.pircbotz.generics.GenericMessageEvent;

public class PrivateMessageEvent extends Event implements GenericMessageEvent {

    private final User user;
    private final String message;

    public PrivateMessageEvent(PircBotZ bot, User user, String message) {
        super(bot);
        this.user = user;
        this.message = message;
    }

    @Override
    public void respond(String response) {
        getUser().send().message(response);
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
