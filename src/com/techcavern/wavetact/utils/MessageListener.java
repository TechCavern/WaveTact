/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.utils;

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
        for (Command Command : GeneralRegistry.Commands) {
            if(m.equalsIgnoreCase(GeneralRegistry.CommandChar + Command.getcomid())){
                if(PermUtils.getPermLevel(event.getBot(), event.getUser(), event.getChannel()) <= Command.getPermLevel()){
                    Command.onCommand(event);
                }else{
                event.getChannel().send().message("Permission Denied");
                }
            }
        }
    }
    
    
}
