/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands;

import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.PermUtils;
import org.pircbotx.PircBotX;
import org.pircbotx.UserLevel;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

public class BasicChanOp extends ListenerAdapter<PircBotX> {
		public void onMessage(MessageEvent<PircBotX> event) throws Exception{
                    String[] messageParts = event.getMessage().split(" ");
                    if(10 <= PermUtils.getPermLevel(event.getBot(), event.getUser(), event.getChannel())){

			 if (messageParts[0].equalsIgnoreCase((GeneralRegistry.CommandChar + "kick"))){
	            	event.getChannel().send().kick(IRCUtils.getUserByNick(event.getChannel(), messageParts[1]));
			 } else 
                             if (messageParts[0].equalsIgnoreCase((GeneralRegistry.CommandChar + "mode"))){
	            	event.getChannel().send().setMode(messageParts[1]);
			 } 
			 }else {
                     event.getChannel().send().message("Permission Denied");
                 }
                         
                         
	} 
                }

	
