package com.techcavern.wavetact.commands;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.PermUtils;

public class CheckUserLevel extends ListenerAdapter<PircBotX> {
	public void onMessage(MessageEvent<PircBotX> event) throws Exception{
		if (event.getMessage().equalsIgnoreCase((GeneralRegistry.CommandChar + "level"))){
			int i = PermUtils.getPermLevel(event.getBot(), event.getUser(), event.getChannel());
			if(i == 9001){
            	event.getChannel().send().message("You are my Master!");
			} else if (i == 10){
				event.getChannel().send().message("You are a Channel Operator!");
			} else if (i == 5){
				event.getChannel().send().message("You are a Trusted User!");
			} else {
				event.getChannel().send().message("You are a Regular User!");
			}
		
		}	
		}
	}

