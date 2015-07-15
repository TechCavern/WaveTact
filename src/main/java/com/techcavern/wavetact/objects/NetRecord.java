package com.techcavern.wavetact.objects;

import org.pircbotx.PircBotX;


public class NetRecord {
    private final String chard;
    private final PircBotX network;

    public NetRecord(String c, PircBotX d) {
        this.chard = c;
        this.network = d;
    }

    public String getProperty() {
        return this.chard;
    }

    public PircBotX getNetwork() {
        return this.network;
    }


}
