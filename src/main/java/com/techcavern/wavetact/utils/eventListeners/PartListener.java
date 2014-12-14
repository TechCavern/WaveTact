/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.utils.eventListeners;

import com.techcavern.wavetact.utils.Constants;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.PartEvent;

import java.util.concurrent.TimeUnit;


/**
 * @author jztech101
 */
public class PartListener extends ListenerAdapter {
    public void onPart(PartEvent event) throws Exception {
        if (Constants.LastLeftChannel.equals(event.getChannel().getName())) {
            Constants.LastLeftChannel = "";
        } else if (event.getUser().getNick().equals(event.getBot().getNick())) {
            int tries = 0;
            do {
                event.getBot().sendIRC().joinChannel(event.getChannel().getName());
                tries++;
                TimeUnit.SECONDS.sleep(30);
            } while (tries < 60 && !event.getBot().getUserBot().getChannels().contains(event.getChannel()));
        }
    }
}
