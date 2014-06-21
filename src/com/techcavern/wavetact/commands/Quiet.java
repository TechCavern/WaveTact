package com.techcavern.wavetact.commands;

import com.techcavern.wavetact.utils.Command;
import java.util.concurrent.TimeUnit;

import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;

import com.techcavern.wavetact.utils.IRCUtils;


public class Quiet extends Command {
    public Quiet(){
    super("Quiet", 10);
}
    @Override   
    public void onCommand(MessageEvent<?> event) throws Exception{
		String[] messageParts = event.getMessage().split(" ");
			
				if(messageParts[1].equalsIgnoreCase("c")||messageParts[1].equalsIgnoreCase("u")||messageParts[1].equalsIgnoreCase("i")){
				if (messageParts.length == 4 && messageParts[2].startsWith("-") == false){
					if(messageParts[3].endsWith("s") || messageParts[3].endsWith("h") || messageParts[3].endsWith("m") || messageParts[3].endsWith("d")){
                                        quiettime time = new quiettime();
					time.run(messageParts[3], messageParts[1],IRCUtils.getUserByNick(event.getChannel(), messageParts[2]), event.getChannel(), event.getBot());
                                        } else {
                                            IRCUtils.SendNotice(event.getBot(), event.getUser(), " Ensure you have specified a valid time (30s = 30 Seconds, 30m = 30 minutes, up to days)");
                                        }
				}else if(messageParts.length < 4 && messageParts[2].startsWith("-") == false){                                        
					quiet(IRCUtils.getUserByNick(event.getChannel(), messageParts[2]), messageParts[1], event.getChannel(), event.getBot());
				}else if(messageParts[2].startsWith("-")){
					unquiet(IRCUtils.getUserByNick(event.getChannel(), messageParts[2].replaceFirst("-", "")), messageParts[1], event.getChannel(), event.getBot());

				}
				else{
				IRCUtils.SendNotice(event.getBot(), event.getUser(), "Syntax: @quiet [ircd code] (-)[User to Quiet] (time in seconds)");
				}
				}
				else{
				IRCUtils.SendNotice(event.getBot(), event.getUser(), "Ensure you have specified the IRCd as the first Argument - i = Inspircd, u = unreal and c = Charybdis or IRCdSeven");
				}

			}
	
		public class quiettime extends Thread{
			public void run(String i, String s, User u, Channel c, PircBotX b) throws InterruptedException{
				quiet(u,s,c,b);
                                if(i.endsWith("s")){
                                    int e = Integer.parseInt(i.replace("s", ""));
                                    TimeUnit.SECONDS.sleep(e);
                                } else if(i.endsWith("m")){
                                    int e = Integer.parseInt(i.replace("m", ""));
                                    TimeUnit.MINUTES.sleep(e);
                                }else  if(i.endsWith("h")){
                                    int e = Integer.parseInt(i.replace("h", ""));
                                    TimeUnit.HOURS.sleep(e);
                                }else  if(i.endsWith("d")){
                                    int e = Integer.parseInt(i.replace("d", ""));
                                    TimeUnit.DAYS.sleep(e);
                                }                                  
				unquiet(u,s,c,b);
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
