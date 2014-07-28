package com.techcavern.wavetact.utils.eventListeners;

import org.pircbotz.PircBotZ;
import org.pircbotz.hooks.ListenerAdapter;
import org.pircbotz.generics.GenericCTCPEvent;


public class CTCPListener extends ListenerAdapter {

    @Override
    public void onGenericCTCP(GenericCTCPEvent event) throws Exception {
        event.respond("Attempt Failed, Try Again!");

    }
}
