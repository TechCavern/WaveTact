package com.techcavern.wavetact.commands;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import com.techcavern.wavetact.utils.*;
public class BasicCommands extends ListenerAdapter<PircBotX> {
	public void ping(MessageEvent<PircBotX> event) throws Exception{
	   IRCUtils.onMessage("ping","pong",event.getChannel(),event.getUser(),event.getBot(),event.getMessage(),0);     
	}
}            

	        


