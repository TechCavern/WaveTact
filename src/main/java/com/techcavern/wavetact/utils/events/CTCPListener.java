package com.techcavern.wavetact.utils.events;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.types.GenericCTCPEvent;


public class CTCPListener extends ListenerAdapter<PircBotX> {

    @Override
    public void onGenericCTCP(GenericCTCPEvent<PircBotX> event) throws Exception {
        event.respond("Attempt Failed, Try Again!");

    }
}
