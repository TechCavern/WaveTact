/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.utils.eventListeners;

import com.techcavern.wavetact.utils.runnables.PrivMsgProcessor;
import org.pircbotz.hooks.ListenerAdapter;
import org.pircbotz.hooks.events.PrivateMessageEvent;

/**
 * @author jztech101
 */
public class PrivMsgListener extends ListenerAdapter {

    @Override
    public void onPrivateMessage(PrivateMessageEvent event) {
        PrivMsgProcessor.PrivMsgProcess(event);
    }
}



