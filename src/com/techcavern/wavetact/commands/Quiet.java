package com.techcavern.wavetact.commands;

import java.util.concurrent.TimeUnit;

import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.output.OutputChannel;

import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.PermUtils;

public class Quiet extends ListenerAdapter<PircBotX> {
	public void onMessage(MessageEvent<PircBotX> event) throws Exception{
		String[] messageParts = event.getMessage().toString().split(" ");
			if (messageParts[0].equalsIgnoreCase((GeneralRegistry.CommandChar + "quiet"))){
			if(10 <= PermUtils.getPermLevel(event.getBot(), event.getUser(), event.getChannel())){
				if (messageParts[3] != null && messageParts[2].startsWith("-") == false){
					quiettime time = new quiettime();
					time.run(Integer.parseInt(messageParts[3]), messageParts[1],event.getUser(), event.getChannel(), event.getBot());
				}
			}else {
            	event.getChannel().send().message("Permission Denied"); 

			}
			}
	}
		public class quiettime extends Thread{
			public void run(int s, String i, User u, Channel c, PircBotX b) throws InterruptedException{
				quiet(u,i,c,b);
				TimeUnit.SECONDS.sleep(s);
				unquiet(u,i,c,b);
		}
		}
		
		public void quiet(User u, String i, Channel c, PircBotX b){
			if(i == "c"){
				OutputChannel o = new OutputChannel(b, c);
				o.setMode("+q ", u.getHostmask());
			}else if(i == "u"){
				OutputChannel o = new OutputChannel(b, c);
				o.setMode("+b ~q:", u.getHostmask());
			}else if(i == "i"){
				OutputChannel o = new OutputChannel(b, c);
				o.setMode("+b m:", u.getHostmask());
			}
			
	
}
		public void unquiet(User u, String i, Channel c, PircBotX b){
			if(i == "c"){
				OutputChannel o = new OutputChannel(b, c);
				o.setMode("-q ", u.getHostmask());
			}else if(i == "u"){
				OutputChannel o = new OutputChannel(b, c);
				o.setMode("-b ~q:", u.getHostmask());
			}else if(i == "i"){
				OutputChannel o = new OutputChannel(b, c);
				o.setMode("-b m:", u.getHostmask());
			}
}
}