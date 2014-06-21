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
                 String m=messageParts[0].toLowerCase();
                        if(m.equalsIgnoreCase((GeneralRegistry.CommandChar + "kick"))|| m.equalsIgnoreCase((GeneralRegistry.CommandChar + "mode")) || m.equalsIgnoreCase((GeneralRegistry.CommandChar + "voice")) || m.equalsIgnoreCase((GeneralRegistry.CommandChar + "op")))
                            if(10 <= PermUtils.getPermLevel(event.getBot(), event.getUser(), event.getChannel())){
                                  if(event.getChannel().isOp(event.getBot().getUserBot())){
                        if (m.equalsIgnoreCase((GeneralRegistry.CommandChar + "somethingawesome"))){
                            if(event.getChannel().getUserLevels(event.getBot().getUserBot()).contains(UserLevel.OP) && event.getChannel().isOwner(event.getUser()) == false && event.getChannel().isSuperOp(event.getUser()) == false){
                             event.getChannel().send().kick(IRCUtils.getUserByNick(event.getChannel(), messageParts[1]));
                            } else if(event.getChannel().getUserLevels(event.getBot().getUserBot()).contains(UserLevel.OWNER)){
                             event.getChannel().send().kick(IRCUtils.getUserByNick(event.getChannel(), messageParts[1]));
                            } else {
                                event.getChannel().send().message("Error: I must be ownered in the channel to kick someone that is protected or ownered");
                            }
            }                else 
                             if (m.equalsIgnoreCase((GeneralRegistry.CommandChar + "mode"))){
	            	event.getChannel().send().setMode(messageParts[1]);
			 } else  if (m.equalsIgnoreCase((GeneralRegistry.CommandChar + "voice"))){
                             if(messageParts[1].startsWith("-"))
	            	event.getChannel().send().deVoice(IRCUtils.getUserByNick(event.getChannel(), messageParts[1].replace("-", "")));
                             else{
                               event.getChannel().send().voice(IRCUtils.getUserByNick(event.getChannel(), messageParts[1]));

                             }
                         } else  if (m.equalsIgnoreCase((GeneralRegistry.CommandChar + "op"))){
            if(messageParts[1].startsWith("-"))
	            	event.getChannel().send().deOp(IRCUtils.getUserByNick(event.getChannel(), messageParts[1].replace("-", "")));
                             else{
                               event.getChannel().send().op(IRCUtils.getUserByNick(event.getChannel(), messageParts[1]));

                             }			 } 
			 }}else{
                     event.getChannel().send().message("Error: I must be opped in the channel to perform the operation");
                            
                            }
                            else {
                     event.getChannel().send().message("Permission Denied");
                 }
                        if(m.equalsIgnoreCase((GeneralRegistry.CommandChar + "owner"))|| m.equalsIgnoreCase((GeneralRegistry.CommandChar + "protect")))
                            if(10 <= PermUtils.getPermLevel(event.getBot(), event.getUser(), event.getChannel())){
                                  if(event.getChannel().isOwner(event.getBot().getUserBot())){
                        if (m.equalsIgnoreCase((GeneralRegistry.CommandChar + "owner"))){
                             if(messageParts[1].startsWith("-"))
	            	event.getChannel().send().deOwner(IRCUtils.getUserByNick(event.getChannel(), messageParts[1].replace("-", "")));
                             else{
                               event.getChannel().send().owner(IRCUtils.getUserByNick(event.getChannel(), messageParts[1]));

                             }
                         } else  if (m.equalsIgnoreCase((GeneralRegistry.CommandChar + "protect"))){
            if(messageParts[1].startsWith("-"))
	            	event.getChannel().send().deSuperOp(IRCUtils.getUserByNick(event.getChannel(), messageParts[1].replace("-", "")));
                             else{
                               event.getChannel().send().superOp(IRCUtils.getUserByNick(event.getChannel(), messageParts[1]));

                             }			 } 
			 }}else{
                     event.getChannel().send().message("Error: I must be ownered in the channel to perform the operation");
                            
                            }
                            else {
                     event.getChannel().send().message("Permission Denied");
                 }
              
                         
	} 
                }

	
