/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.eventListeners;

import com.techcavern.wavetact.runnables.PrivMsgProcessor;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.PrivateMessageEvent;

/**
 * @author jztech101
 */
public class PrivMsgListener extends ListenerAdapter {

    @Override
    public void onPrivateMessage(PrivateMessageEvent event) throws Exception {
        PrivMsgProcessor.PrivMsgProcess(event);
    }
}



