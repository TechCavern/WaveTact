/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.chanhalfop;

import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.utils.objects.Command;
import com.techcavern.wavetact.utils.objects.UTime;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author jztech101
 */

public class Topic extends Command {

    public Topic() {
        super(GeneralUtils.toArray("topic t"), 6, "Topic [Seperator] [a(add)/sw(switch)/+[topic #]/-[topic #]/(Insert message to replace whole topic)/ss(switch seperator)/r(revert)] (Messages to add)(Integer to swap)(seperator to change to) (Integer to swap)");
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {
        StringUtils.split(event.getChannel().getTopic(), args[0]);
        List<String> topic = new LinkedList(Arrays.asList(StringUtils.split(event.getChannel().getTopic(), args[0])));
        List<String> newtopic = new LinkedList(Arrays.asList(StringUtils.split(event.getChannel().getTopic(), args[0])));
        if(args[1].equalsIgnoreCase("a")){
            event.getChannel().send().setTopic(event.getChannel().getTopic() + " " + args[0] + " " + GeneralUtils.buildMessage(2, args.length, args));
            saveTopic(event);
        }else if(args[1].startsWith("+")){
            newtopic.set(Integer.parseInt(args[1].replaceFirst("\\+", ""))-1, " " + GeneralUtils.buildMessage(2,args.length, args));
            event.getChannel().send().setTopic(StringUtils.join(newtopic, args[0]));
            saveTopic(event);
        }else if(args[1].startsWith("-")){
            newtopic.remove(Integer.parseInt(args[1].replaceFirst("\\-", ""))-1);
            event.getChannel().send().setTopic(StringUtils.join(newtopic, args[0]));
            saveTopic(event);
        }else if(args[1].equalsIgnoreCase("sw") || args[1].equalsIgnoreCase("swap")){
            newtopic.set((Integer.parseInt(args[2])-1)," " + topic.get(Integer.parseInt(args[3])-1));
            newtopic.set((Integer.parseInt(args[3])-1), " " + topic.get(Integer.parseInt(args[2])-1));
            event.getChannel().send().setTopic(StringUtils.join(newtopic, args[0]));
            saveTopic(event);
        }else if (args[1].equalsIgnoreCase("ss")) {
            event.getChannel().send().setTopic(event.getChannel().getTopic().replace(args[0], args[2]));
            saveTopic(event);
        }else if (args[1].equalsIgnoreCase("r")){
            UTime oldTopic = GetUtils.getTopic(event.getChannel().getName(), event.getBot().getServerInfo().getNetwork());
            if(oldTopic != null) {
                event.getChannel().send().setTopic(oldTopic.getSomething());
                GeneralRegistry.Topic.remove(oldTopic);
            }else{
                event.getChannel().send().message("Error: No Reversal Possible");
            }
        }else{
            event.getChannel().send().setTopic(GeneralUtils.buildMessage(1, args.length, args));
            saveTopic(event);
        }
    }
    void saveTopic(MessageEvent<?> event){
        GeneralRegistry.Topic.add(new UTime(event.getChannel().getTopic(), event.getBot().getServerInfo().getNetwork(), "Topic", event.getChannel().getName(), GeneralUtils.getMilliSeconds("30s"), System.currentTimeMillis()));
    }
}
