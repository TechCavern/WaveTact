package com.techcavern.wavetact.commands;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import com.techcavern.wavetact.utils.*;
public class CheckPermLevel extends ListenerAdapter<PircBotX> {
	        public void onMessage(MessageEvent<PircBotX> event) {
	                if (event.getMessage().equalsIgnoreCase((GeneralRegistry.getCommandChar()+"cperm"))){
	                	event.respond("hi");
	                	}
	             
	}
	        
}


