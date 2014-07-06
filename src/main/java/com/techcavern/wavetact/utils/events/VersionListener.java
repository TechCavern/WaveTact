package com.techcavern.wavetact.utils.events;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.VersionEvent;

/**
 * Created by jztech101 on 7/6/14.
 */
public class VersionListener extends ListenerAdapter<PircBotX> {

    @Override
    public void onVersion(VersionEvent<PircBotX> event) throws Exception {
        event.respond("Wavetact - based on PircBotX");
    }
}