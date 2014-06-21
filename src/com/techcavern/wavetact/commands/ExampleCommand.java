package com.techcavern.wavetact.commands;

import com.techcavern.wavetact.utils.Command;
import org.pircbotx.hooks.events.MessageEvent;


public class ExampleCommand extends Command{
    public ExampleCommand(){
        super("blah", 10);
    }
    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception{
		event.getChannel().send().message("hi");
				
    }
}

//Purely here for future reference