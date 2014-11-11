/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.utils.event;

import com.techcavern.wavetact.utils.runnables.PrivMsgProcessor;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.PrivateMessageEvent;

/**
 * @author jztech101
 */
public class PrivMsgListener extends ListenerAdapter<PircBotX> {

    @Override
    public void onPrivateMessage(PrivateMessageEvent<PircBotX> event) throws Exception {
        PrivMsgProcessor.PrivMsgProcess(event);
    }
}



