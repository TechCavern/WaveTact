/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.chanhalfop;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.ChanHOPCMD;
import com.techcavern.wavetact.utils.*;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import com.techcavern.wavetact.utils.objects.UTime;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author jztech101
 */
@CMD
@ChanHOPCMD
public class Topic extends GenericCommand {

    public Topic() {
        super(GeneralUtils.toArray("topic"), 7, "Topic [Seperator] [a(add)/sw(switch)/+[topic #]/-[topic #]/(Insert message to replace whole topic)/ss(switch seperator)/r(revert)] (Messages to add)(Integer to swap)(seperator to change to) (Integer to swap)", "manages the topic", true);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        StringUtils.split(channel.getTopic(), args[0]);
        List<String> topic = new LinkedList(Arrays.asList(StringUtils.split(channel.getTopic(), args[0])));
        List<String> newtopic = new LinkedList(Arrays.asList(StringUtils.split(channel.getTopic(), args[0])));
        if (args[1].equalsIgnoreCase("a") || args[1].equalsIgnoreCase("add")) {
            channel.send().setTopic(channel.getTopic() + " " + args[0] + " " + GeneralUtils.buildMessage(2, args.length, args));
            saveTopic(channel, network);
        } else if (args[1].startsWith("+")) {
            newtopic.set(Integer.parseInt(args[1].replaceFirst("\\+", "")) - 1, " " + GeneralUtils.buildMessage(2, args.length, args) + " ");
            channel.send().setTopic(StringUtils.join(newtopic, args[0]));
            saveTopic(channel, network);
        } else if (args[1].startsWith("-")) {
            newtopic.remove(Integer.parseInt(args[1].replaceFirst("\\-", "")) - 1);
            channel.send().setTopic(StringUtils.join(newtopic, args[0]));
            saveTopic(channel, network);
        } else if (args[1].equalsIgnoreCase("sw") || args[1].equalsIgnoreCase("swap") || args[1].equalsIgnoreCase("switch")) {
            newtopic.set((Integer.parseInt(args[2]) - 1), topic.get(Integer.parseInt(args[3]) - 1));
            newtopic.set((Integer.parseInt(args[3]) - 1), topic.get(Integer.parseInt(args[2]) - 1));
            channel.send().setTopic(StringUtils.join(newtopic, args[0]));
            saveTopic(channel, network);
        } else if (args[1].equalsIgnoreCase("ss") || args[1].equalsIgnoreCase("switchseperator")) {
            channel.send().setTopic(channel.getTopic().replace(args[0], args[2]));
            saveTopic(channel, network);
        } else if (args[1].equalsIgnoreCase("r") || args[1].equalsIgnoreCase("revert")) {
            UTime oldTopic = GetUtils.getTopic(channel.getName(), network.getServerInfo().getNetwork());
            if (oldTopic != null) {
                channel.send().setTopic(oldTopic.getHostmask());
                Constants.Topic.remove(oldTopic);
            } else {
                ErrorUtils.sendError(user, "Error: No Reversal Possible");
            }
        } else {
            channel.send().setTopic(GeneralUtils.buildMessage(1, args.length, args));
            saveTopic(channel, network);
        }
    }

    void saveTopic(Channel channel, PircBotX Bot) {
        Constants.Topic.add(new UTime(channel.getTopic(), Bot.getServerInfo().getNetwork(), "Topic", channel.getName(), GeneralUtils.getMilliSeconds("30s"), System.currentTimeMillis()));
    }
}
