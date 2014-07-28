package org.pircbotz;

import java.util.Iterator;

abstract class ChannelModeHandler {

    private final char mode;

    public ChannelModeHandler(char mode) {
        this.mode = mode;
    }

    public char getMode() {
        return mode;
    }

    public abstract void handleMode(PircBotZ bot, Channel channel, User sourceUser, Iterator<String> params, boolean adding, boolean dispatchEvent);
}
