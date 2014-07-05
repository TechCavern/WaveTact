/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.chanop;

import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.objects.Command;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.Arrays;
import java.util.List;

/**
 * @author jztech101
 */

public class Topic extends Command {

    public Topic() {
        super(GeneralUtils.toArray("topic t"), 10, "Topic ");
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {

        List<String> topic = Arrays.asList(event.getChannel().getTopic().split(args[0]));
        if(args.length<2){
            event.getChannel().send().setTopic(GeneralUtils.buildMessage(0,args.length, args));
        }
        else if(args[1].startsWith("+")){
            event.getChannel().send().setTopic(event.getChannel().getTopic().replace(topic.get(Integer.parseInt(args[1].replace("+", ""))), GeneralUtils.buildMessage(2,args.length, args)));
        }else if(args[1].startsWith("-")){
            event.getChannel().send().setTopic(event.getChannel().getTopic().replace(topic.get(Integer.parseInt(args[1].replace("-", ""))), ""));
        }else if(args[1].equalsIgnoreCase("switch")){
            event.getChannel().send().setTopic(event.getChannel().getTopic().replace(topic.get(Integer.parseInt(args[2])), topic.get(Integer.parseInt(args[3]))).replace(topic.get(Integer.parseInt(args[3])), topic.get(Integer.parseInt(args[2]))));
        }
    }
}
