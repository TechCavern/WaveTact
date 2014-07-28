package org.pircbotz.hooks.events;

import org.pircbotz.PircBotZ;
import org.pircbotz.User;
import org.pircbotz.hooks.Event;
import org.pircbotz.generics.GenericUserEvent;

public class UserModeEvent extends Event implements GenericUserEvent {

    private final User user;
    private final User recipient;
    private final String mode;

    public UserModeEvent(PircBotZ bot, User user, User recipient, String mode) {
        super(bot);
        this.user = user;
        this.recipient = recipient;
        this.mode = mode;
    }

    @Override
    public void respond(String response) {
        getUser().send().message(response);
    }

    @Override
    public User getUser() {
        return user;
    }

    public User getRecipient() {
        return recipient;
    }

    public String getMode() {
        return mode;
    }
}
