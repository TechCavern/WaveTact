package com.techcavern.wavetact.commands;

import java.util.concurrent.TimeUnit;

import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.PermUtils;
import com.techcavern.wavetact.utils.IRCUtils;


public class Quiet extends ListenerAdapter<PircBotX> {
	public void onMessage(MessageEvent<PircBotX> event) throws Exception{
		String[] messageParts = event.getMessage().split(" ");
			if (messageParts[0].equalsIgnoreCase((GeneralRegistry.CommandChar + "quiet"))){
			if(10 <= PermUtils.getPermLevel(event.getBot(), event.getUser(), event.getChannel())){
				if(messageParts[1].equalsIgnoreCase("c")||messageParts[1].equalsIgnoreCase("u")||messageParts[1].equalsIgnoreCase("i")){
				if (messageParts[3] != null && messageParts[2].startsWith("-") == false){
					quiettime time = new quiettime();
					time.run(Integer.parseInt(messageParts[3]), messageParts[1],IRCUtils.getUserByNick(event.getChannel(), messageParts[2]), event.getChannel(), event.getBot());
				}else if(messageParts[3] == null && messageParts[2].startsWith("-") == false){
					quiet(IRCUtils.getUserByNick(event.getChannel(), messageParts[2]), messageParts[1], event.getChannel(), event.getBot());
				}else if(messageParts[2].startsWith("-")){
					unquiet(IRCUtils.getUserByNick(event.getChannel(), messageParts[2]), messageParts[1], event.getChannel(), event.getBot());

				}
				else{
				IRCUtils.SendNotice(event.getBot(), event.getUser(), "Syntax: @quiet [ircd code] (-)[User to Quiet] (time in seconds)");
				}
				}
				else{
				IRCUtils.SendNotice(event.getBot(), event.getUser(), "Ensure you have specified the IRCd as the first Argument - i = Inspircd, u = unreal and c = Charybdis or IRCdSeven");
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
			if(i.equalsIgnoreCase("c")){
				IRCUtils.setMode(c, b, "+q ", u);
			}else if(i.equalsIgnoreCase("u")){
				IRCUtils.setMode(c, b, "+b ~q:", u);
			}else if(i.equalsIgnoreCase("i")){
				IRCUtils.setMode(c, b, "+b m:", u);
			}
			
	
}
		public void unquiet(User u, String i, Channel c, PircBotX b){
			if(i.equalsIgnoreCase("c")){
				IRCUtils.setMode(c, b, "-q ", u);
			}else if(i.equalsIgnoreCase("u")){
				IRCUtils.setMode(c, b, "-b ~q:", u);
			}else if(i.equalsIgnoreCase("i")){
				IRCUtils.setMode(c, b, "-b m:", u);
			}
}
}
