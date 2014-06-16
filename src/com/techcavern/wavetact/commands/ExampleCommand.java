package com.techcavern.wavetact.commands;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import com.techcavern.wavetact.utils.GeneralRegistry;

public class ExampleCommand extends ListenerAdapter<PircBotX> {
		public void onMessage(MessageEvent<PircBotX> event) throws Exception{
			 if (event.getMessage().equalsIgnoreCase((GeneralRegistry.CommandChar + "test"))){
	            	event.getChannel().send().message("test");

			 }
	} 
	
}

//Purely here for future reference