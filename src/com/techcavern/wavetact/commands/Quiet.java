package com.techcavern.wavetact.commands;

import java.util.concurrent.TimeUnit;

import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.output.OutputChannel;
import org.pircbotx.output.OutputUser;

import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.PermUtils;

public class Quiet extends ListenerAdapter<PircBotX> {
		public void onMessage(MessageEvent<PircBotX> event) throws Exception{
			if (event.getMessage().startsWith((GeneralRegistry.CommandChar + "quiet"))){
		 }
			}
		public class quietchary extends Thread{
			public void run(int s, User u, Channel c, PircBotX b) throws InterruptedException{
				OutputChannel o = new OutputChannel(b, c);
				o.setMode("+q", u);
				TimeUnit.SECONDS.sleep(s);
				o.setMode("-q", u);
		}
	
}
}
