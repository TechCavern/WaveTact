package com.techcavern.wavetact.utils.objects;

import com.techcavern.wavetact.utils.GeneralRegistry;
import org.pircbotz.PircBotZ;


public class CommandChar {
    private final String chard;
    private final PircBotZ bot;

    public CommandChar(String c, PircBotZ d) {
        this.chard = c;
        this.bot = d;
        create();
    }

    void create() {
        GeneralRegistry.CommandChars.add(this);
    }

    public String getCommandChar() {
        return this.chard;
    }

    public PircBotZ getBot() {
        return this.bot;
    }


}
