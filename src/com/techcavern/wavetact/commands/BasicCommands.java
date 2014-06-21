package com.techcavern.wavetact.commands;
import org.pircbotx.hooks.events.MessageEvent;

import com.techcavern.wavetact.utils.*;
public class BasicCommands{
	public class ping extends Command{
    public ping(){
        super("ping", 0);
    }
    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception{
		event.getChannel().send().message("pong");
				
    }
}
        public class potato extends Command{
    public potato(){
        super("potato", 0);
    }
    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception{
		event.getChannel().send().action("is a potato");
				
    }
}
        public class pong extends Command{
    public pong(){
        super("pong", 0);
    }
    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception{
		event.getChannel().send().message("ping");
				
    }
}
       public class Source extends Command{
    public Source(){
        super("source", 0);
    }
    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception{
		event.getChannel().send().message("http://github.com/TechCavern/WaveTact");
				
    }
}    
         


   
}


	        


