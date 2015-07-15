package com.techcavern.wavetact.objects;

import org.pircbotx.PircBotX;


public class NetMessage {
    private final String message;
    private final PircBotX network;

    public NetMessage(String c, PircBotX d) {
        this.message = c;
        this.network = d;
    }

    public String getProperty() {
        return this.message;
    }

    public PircBotX getNetwork() {
        return this.network;
    }


}
