package com.techcavern.wavetact.commands;
import org.pircbotx.hooks.events.MessageEvent;

import com.techcavern.wavetact.utils.*;
import org.pircbotx.UserLevel;
public class BasicCommands{
	public class ping extends Command{
    public ping(){
        super("ping", 0);
    }
    @Override
    public void onCommand(MessageEvent<?> event) throws Exception{
		event.getChannel().send().message("pong");
				
    }
}
        public class pong extends Command{
    public pong(){
        super("pong", 0);
    }
    @Override
    public void onCommand(MessageEvent<?> event) throws Exception{
		event.getChannel().send().message("ping");
				
    }
}
       public class Source extends Command{
    public Source(){
        super("source", 0);
    }
    @Override
    public void onCommand(MessageEvent<?> event) throws Exception{
		event.getChannel().send().message("http://github.com/TechCavern/WaveTact");
				
    }
}    
          public class SomethingAwesome extends Command{
    public SomethingAwesome(){
        super("somethingawesome", 0);
    }
    @Override
    public void onCommand(MessageEvent<?> event) throws Exception{
if(event.getChannel().getUserLevels(event.getBot().getUserBot()).contains(UserLevel.OP) && event.getChannel().isOwner(event.getUser()) == false && event.getChannel().isSuperOp(event.getUser()) == false){
                             event.getChannel().send().kick(event.getUser(),"http://bit.ly/1c9vo1S");
                            } else if(event.getChannel().getUserLevels(event.getBot().getUserBot()).contains(UserLevel.OWNER)){
                             event.getChannel().send().kick(event.getUser(),"http://bit.ly/1c9vo1S");
                            }else {
                                event.getChannel().send().message("http://bit.ly/1c9vo1S");

                            }				
    }
} 
public class say extends Command{
    public say(){
        super("say", 5);
    }
    @Override
    public void onCommand(MessageEvent<?> event) throws Exception{
                    event.getChannel().send().message(event.getMessage().replace(GeneralRegistry.CommandChar+"say ",""));
				
    }
}

   public class act extends Command{
    public act(){
        super("act", 5);
    }
    @Override
    public void onCommand(MessageEvent<?> event) throws Exception{
                    event.getChannel().send().action(event.getMessage().replace(GeneralRegistry.CommandChar+"act ",""));
				
    }
}
}


	        


