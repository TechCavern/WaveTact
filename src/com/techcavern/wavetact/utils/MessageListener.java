/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.utils;

import com.techcavern.wavetact.utils.GeneralRegistry.Commands;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

/**
 *
 * @author jztech101
 */
public class MessageListener extends ListenerAdapter<PircBotX> {
    
    @Override
    public void onMessage(MessageEvent<PircBotX> event) throws Exception{
        String[] messageParts = event.getMessage().split(" ");
         String m=messageParts[0].toLowerCase(); 
        for(int i = 0; i < GeneralRegistry.Commands.size(); i++){
            
        
    }
    }
    
    
}
