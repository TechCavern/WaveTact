/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands;

import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.PermUtils;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

/**
 *
 * @author jztech101
 */
public class ControllerCommands extends ListenerAdapter<PircBotX> {
		public void onMessage(MessageEvent<PircBotX> event) throws Exception{
                             String[] messageParts = event.getMessage().split(" ");
                 String m=messageParts[0].toLowerCase();
                    if(m.equalsIgnoreCase((GeneralRegistry.CommandChar + "join"))){
                        
                    
                            if(9001 <= PermUtils.getPermLevel(event.getBot(), event.getUser(),  event.getChannel())){
                            if(m.equalsIgnoreCase((GeneralRegistry.CommandChar+"join"))){
                                event.getBot().sendIRC().joinChannel(messageParts[1]);
                            }  
                            }
}
                }
}