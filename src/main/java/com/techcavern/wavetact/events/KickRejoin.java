/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.events;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.KickEvent;

import java.util.concurrent.TimeUnit;


/**
 * @author jztech101
 */
public class KickRejoin extends ListenerAdapter<PircBotX> {
    public void onKick(KickEvent<PircBotX> event) throws Exception {
        if (event.getRecipient() == event.getBot().getUserBot()) {
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
