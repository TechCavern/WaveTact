/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.utils.event;

import com.techcavern.wavetact.utils.runnables.ChanMsgProcessor;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * @author jztech101
 */
public class ChanMsgListener extends ListenerAdapter<PircBotX> {
    @Override
    public void onMessage(MessageEvent<PircBotX> event) throws Exception {
        ChanMsgProcessor.ChanMsgProcess(event);
    }
}



