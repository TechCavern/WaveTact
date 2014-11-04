package com.techcavern.wavetact.utils.objects;

import org.pircbotx.PircBotX;


public class NetProperty {
    private final String chard;
    private final PircBotX bot;

    public NetProperty(String c, PircBotX d) {
        this.chard = c;
        this.bot = d;
    }

    public String getProperty() {
        return this.chard;
    }

    public PircBotX getBot() {
        return this.bot;
    }


}
