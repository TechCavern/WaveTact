package com.techcavern.wavetact.objects;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.WhoisEvent;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Created by roelf on 12/27/14.
 */
public class CachedWhoisEvent {
    private WhoisEvent whoisEvent;
    private PircBotX network;
    private String user;

    public CachedWhoisEvent(WhoisEvent who, PircBotX network, String user) {
        this.whoisEvent = who;
        this.network = network;
        this.user = user;
    }
    public String getUser(){
        return this.user;
    }
    public PircBotX getNetwork(){
        return this.network;
    }
    public WhoisEvent getWhoisEvent(){
        return whoisEvent;
    }
}
