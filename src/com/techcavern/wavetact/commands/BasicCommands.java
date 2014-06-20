package com.techcavern.wavetact.commands;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import com.techcavern.wavetact.utils.*;
public class BasicCommands extends ListenerAdapter<PircBotX> {
	public void onMessage(MessageEvent<PircBotX> event) throws Exception{
	   IRCUtils.onMessage("ping","pong",event.getChannel(),event.getUser(),event.getBot(),event.getMessage(),0);     
	   IRCUtils.onMessage("pong","ping",event.getChannel(),event.getUser(),event.getBot(),event.getMessage(),0);
	   IRCUtils.onMessage("Source","https://github.com/TechCavern/WaveTact",event.getChannel(),event.getUser(),event.getBot(),event.getMessage(),0);

	}
}            

	        


