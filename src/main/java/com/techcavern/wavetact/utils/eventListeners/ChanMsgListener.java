/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.utils.eventListeners;

import com.techcavern.wavetact.utils.runnables.ChanMsgProcessor;
import org.pircbotz.PircBotZ;
import org.pircbotz.hooks.ListenerAdapter;
import org.pircbotz.hooks.events.MessageEvent;

/**
 * @author jztech101
 */
public class ChanMsgListener extends ListenerAdapter {
    @Override
    public void onMessage(MessageEvent event) throws Exception {
        ChanMsgProcessor.ChanMsgProcess(event);
    }
}



