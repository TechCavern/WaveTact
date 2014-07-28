/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.utils.eventListeners;

import org.pircbotz.hooks.ListenerAdapter;
import org.pircbotz.hooks.events.KickEvent;

import java.util.concurrent.TimeUnit;


/**
 * @author jztech101
 */
public class KickListener extends ListenerAdapter {
    public void onKick(KickEvent event) throws Exception {
        if (event.getRecipient() == event.getBot().getUserBot() && !event.getReason().toLowerCase().contains("banned")) {
            event.getBot().sendIRC().joinChannel(event.getChannel().getName());
            TimeUnit.SECONDS.sleep(30);
            event.getBot().sendIRC().joinChannel(event.getChannel().getName());
            TimeUnit.MINUTES.sleep(5);
            event.getBot().sendIRC().joinChannel(event.getChannel().getName());
            TimeUnit.MINUTES.sleep(30);
            event.getBot().sendIRC().joinChannel(event.getChannel().getName());
        }
    }
}
