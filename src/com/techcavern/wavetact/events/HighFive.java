package com.techcavern.wavetact.events;

import java.util.ArrayList;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.List;
import org.pircbotx.Channel;

public class HighFive extends ListenerAdapter<PircBotX> {
    public List<String> HighFives = new ArrayList();
		public void onMessage(MessageEvent<PircBotX> event) throws Exception{
			 if (event.getMessage().toLowerCase().startsWith("o/")||event.getMessage().toLowerCase().startsWith("\\o")||event.getMessage().toLowerCase().startsWith("0/") || event.getMessage().toLowerCase().startsWith("\\0")||event.getMessage().toLowerCase().endsWith("o/")||event.getMessage().toLowerCase().endsWith("\\o")||event.getMessage().toLowerCase().endsWith("0/") || event.getMessage().toLowerCase().endsWith("\\0")){
                             
                              HighFives.add(event.getUser().getNick());
                              CheckIfTwoHighFives(event.getChannel());
			 }
	} 
        public void CheckIfTwoHighFives(Channel c){
            if(HighFives.size()%2 == 0 && !HighFives.isEmpty()){
                c.send().message(HighFives.get(HighFives.size()-1)+ " o/ * \\o " + HighFives.get(HighFives.size()-2));
            }
        }
	
}
