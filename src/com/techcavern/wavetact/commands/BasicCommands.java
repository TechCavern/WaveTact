package com.techcavern.wavetact.commands;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import com.techcavern.wavetact.utils.*;
import org.pircbotx.UserLevel;
public class BasicCommands extends ListenerAdapter<PircBotX> {
	public void onMessage(MessageEvent<PircBotX> event) throws Exception{
	   IRCUtils.onMessage("ping","pong",event.getChannel(),event.getUser(),event.getBot(),event.getMessage(),0);     
	   IRCUtils.onMessage("pong","ping",event.getChannel(),event.getUser(),event.getBot(),event.getMessage(),0);
	   IRCUtils.onMessage("Source","https://github.com/TechCavern/WaveTact",event.getChannel(),event.getUser(),event.getBot(),event.getMessage(),0);
           String m = event.getMessage().toLowerCase(); 
           if (m.equalsIgnoreCase((GeneralRegistry.CommandChar + "somethingawesome"))){
                            if(event.getChannel().getUserLevels(event.getBot().getUserBot()).contains(UserLevel.OP) && event.getChannel().isOwner(event.getUser()) == false && event.getChannel().isSuperOp(event.getUser()) == false){
                             event.getChannel().send().kick(event.getUser(),"http://bit.ly/1c9vo1S");
                            } else if(event.getChannel().getUserLevels(event.getBot().getUserBot()).contains(UserLevel.OWNER)){
                             event.getChannel().send().kick(event.getUser(),"http://bit.ly/1c9vo1S");
                            }else {
                                event.getChannel().send().message("http://bit.ly/1c9vo1S");

                            }
            }
    if(m.startsWith(GeneralRegistry.CommandChar+"say") || m.startsWith(GeneralRegistry.CommandChar+"act")){
        
        if(PermUtils.getPermLevel(event.getBot(), event.getUser(), event.getChannel())== 5){

                if(m.startsWith(GeneralRegistry.CommandChar+"say")){

                    event.getChannel().send().message(event.getMessage().replace(GeneralRegistry.CommandChar+"say ",""));
                               
                } else if(m.startsWith(GeneralRegistry.CommandChar+"act")){

                    event.getChannel().send().action(event.getMessage().replace(GeneralRegistry.CommandChar+"act ",""));
                  
                }
            }else {
            event.getChannel().send().message("Permission Denied");
        }
    }
}  
}


	        


