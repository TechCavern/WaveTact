package org.pircbotz.hooks.events;

import org.pircbotz.PircBotZ;
import org.pircbotz.hooks.Event;

public class NickAlreadyInUseEvent extends Event {

    private final String usedNick;
    private final String autoNewNick;
    private final boolean autoNickChange;

    public NickAlreadyInUseEvent(PircBotZ bot, String usedNick, String autoNewNick, boolean autoNickChange) {
        super(bot);
        this.usedNick = usedNick;
        this.autoNewNick = autoNewNick;
        this.autoNickChange = autoNickChange;
    }

    @Override
    public void respond(String newNick) {
        getBot().sendIRC().changeNick(newNick);
    }

    public String getUsedNick() {
        return usedNick;
    }

    public String getAutoNewNick() {
        return autoNewNick;
    }

    public boolean isAutoNickChange() {
        return autoNickChange;
    }
}
