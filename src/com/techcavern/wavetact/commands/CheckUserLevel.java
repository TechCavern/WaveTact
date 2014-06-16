package com.techcavern.wavetact.commands;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.PermUtils;

public class CheckUserLevel extends ListenerAdapter<PircBotX> {
	public void onMessage(MessageEvent<PircBotX> event) throws Exception{
		if (event.getMessage().equalsIgnoreCase((GeneralRegistry.CommandChar + "level"))){
			if(PermUtils.isController(event.getBot(),event.getUser())){
            	event.getChannel().send().message("You are my Master");
			} else if(event.getUser().getChannelsOpIn().contains(event.getChannel()) || event.getUser().getChannelsSuperOpIn().contains(event.getChannel())||event.getUser().getChannelsOwnerIn().contains(event.getChannel())){
            	event.getChannel().send().message("You are a Channel Op!");
			}else if(event.getUser().getChannelsHalfOpIn().contains(event.getChannel()) || event.getUser().getChannelsVoiceIn().contains(event.getChannel())){
				event.getChannel().send().message("You are a Trusted User!");
				} else {
            	event.getChannel().send().message("You are a Regular User!");

			}
		}

}
	}

