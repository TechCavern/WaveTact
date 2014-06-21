/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.chanop;

import com.techcavern.wavetact.utils.Command;
import java.util.Arrays;
import java.util.List;
import org.pircbotx.hooks.events.MessageEvent;

/**
 *
 * @author jztech101
 */
public class Topic extends Command{
    public Topic(){
        super("topic", 10);
    }
    
    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception{

            if(args.length == 4){
                List<String> t = Arrays.asList(event.getChannel().getTopic().split(args[0]));

            if(args[1].equalsIgnoreCase("switchpart")){
               
		event.getChannel().send().message(event.getChannel().getTopic().replaceFirst(t.get(Integer.parseInt(args[2])), t.get(Integer.parseInt(args[3]))));
                event.getChannel().send().message(event.getChannel().getTopic().replaceFirst(t.get(Integer.parseInt(args[3])), t.get(Integer.parseInt(args[2]))));
		
    }else if (args[1].equalsIgnoreCase("setpart")){
           event.getChannel().send().message(event.getChannel().getTopic().replaceFirst(t.get(Integer.parseInt(args[2])), args[3]));

    }}
            else if(args.length == 3){
                                        List<String> t = Arrays.asList(event.getChannel().getTopic().split(args[0]));

                if(args[1].equalsIgnoreCase("addpart")){
                     event.getChannel().send().message(event.getChannel().getTopic().replace(t.get(t.size()), t.get(t.size()) + " "+ args[0] + " " +args[2]));
                }}
            else if(args.length == 2){
                                        List<String> t = Arrays.asList(event.getChannel().getTopic().split(args[0]));

                if(args[1].equalsIgnoreCase("removepart")){
                    if(Integer.parseInt(args[2]) == 1){
                        event.getChannel().getTopic().replace(t.get(1) + args[0], "" );
                    }else{
                        event.getChannel().getTopic().replace(args[0] + t.get(1) , "" );
                    }
                }}else{
                if(args[0].equalsIgnoreCase("set")){
                    event.getChannel().send().setTopic(args[1]);
                }else if(args[0].equalsIgnoreCase("replace")){
                       event.getChannel().getTopic().replace(args[1],args[2] );

                }else if(args[0].equalsIgnoreCase("remove")){
                       event.getChannel().getTopic().replace(args[1],"");

                }
               
}
}
}

