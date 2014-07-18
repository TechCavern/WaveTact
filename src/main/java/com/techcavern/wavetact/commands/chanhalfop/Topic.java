/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.chanhalfop;

import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import com.techcavern.wavetact.utils.objects.UTime;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author jztech101
 */

public class Topic extends GenericCommand {

    public Topic() {
        super(GeneralUtils.toArray("topic t"), 6, "Topic [Seperator] [a(add)/sw(switch)/+[topic #]/-[topic #]/(Insert message to replace whole topic)/ss(switch seperator)/r(revert)] (Messages to add)(Integer to swap)(seperator to change to) (Integer to swap)");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate,int UserPermLevel, String... args) throws Exception {
        StringUtils.split(channel.getTopic(), args[0]);
        List<String> topic = new LinkedList(Arrays.asList(StringUtils.split(channel.getTopic(), args[0])));
        List<String> newtopic = new LinkedList(Arrays.asList(StringUtils.split(channel.getTopic(), args[0])));
        if(args[1].equalsIgnoreCase("a")){
            channel.send().setTopic(channel.getTopic() + " " + args[0] + " " + GeneralUtils.buildMessage(2, args.length, args));
            saveTopic(channel, Bot);
        }else if(args[1].startsWith("+")){
            newtopic.set(Integer.parseInt(args[1].replaceFirst("\\+", ""))-1, " " + GeneralUtils.buildMessage(2,args.length, args));
            channel.send().setTopic(StringUtils.join(newtopic, args[0]));
            saveTopic(channel, Bot);
        }else if(args[1].startsWith("-")){
            newtopic.remove(Integer.parseInt(args[1].replaceFirst("\\-", ""))-1);
            channel.send().setTopic(StringUtils.join(newtopic, args[0]));
            saveTopic(channel, Bot);
        }else if(args[1].equalsIgnoreCase("sw") || args[1].equalsIgnoreCase("swap")){
            newtopic.set((Integer.parseInt(args[2])-1)," " + topic.get(Integer.parseInt(args[3])-1));
            newtopic.set((Integer.parseInt(args[3])-1), " " + topic.get(Integer.parseInt(args[2])-1));
            channel.send().setTopic(StringUtils.join(newtopic, args[0]));
            saveTopic(channel, Bot);
        }else if (args[1].equalsIgnoreCase("ss")) {
            channel.send().setTopic(channel.getTopic().replace(args[0], args[2]));
            saveTopic(channel, Bot);
        }else if (args[1].equalsIgnoreCase("r")){
            UTime oldTopic = GetUtils.getTopic(channel.getName(), Bot.getServerInfo().getNetwork());
            if(oldTopic != null) {
                channel.send().setTopic(oldTopic.getSomething());
                GeneralRegistry.Topic.remove(oldTopic);
            }else{
                channel.send().message("Error: No Reversal Possible");
            }
        }else{
            channel.send().setTopic(GeneralUtils.buildMessage(1, args.length, args));
            saveTopic(channel, Bot);
        }
    }
    void saveTopic(Channel channel, PircBotX Bot){
        GeneralRegistry.Topic.add(new UTime(channel.getTopic(), Bot.getServerInfo().getNetwork(), "Topic", channel.getName(), GeneralUtils.getMilliSeconds("30s"), System.currentTimeMillis()));
    }
}
