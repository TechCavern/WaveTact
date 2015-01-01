/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.ircCommands.chanhalfop;

import com.techcavern.wavetact.annot.IRCCMD;
import static com.techcavern.wavetactdb.Tables.*;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Record;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author jztech101
 */
@IRCCMD

public class Topic extends IRCCommand {

    public Topic() {
        super(GeneralUtils.toArray("topic"), 7, "Topic [separator] [a(add)/sw(switch)/+[topic #]/-[topic #]/(insert message to replace whole topic)/ss(switch separator)/r(revert)] (messages to add)(integer to swap)(separator to change to) (integer to swap)", "Manages the topic", true);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        StringUtils.split(channel.getTopic(), args[0]);
        List<String> topic = new LinkedList(Arrays.asList(StringUtils.split(channel.getTopic(), args[0])));
        List<String> newtopic = new LinkedList(Arrays.asList(StringUtils.split(channel.getTopic(), args[0])));
        if (args[1].equalsIgnoreCase("a") || args[1].equalsIgnoreCase("add")) {
            channel.send().setTopic(channel.getTopic() + " " + args[0] + " " + GeneralUtils.buildMessage(2, args.length, args) + " ");
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
            Record oldTopic = DatabaseUtils.getChannelProperty(IRCUtils.getNetworkNameByNetwork(network), channel.getName(), "topic");
            if (oldTopic != null) {
                channel.send().setTopic(oldTopic.getValue(CHANNELPROPERTY.VALUE));
            } else {
                ErrorUtils.sendError(user, "Error: no reversal possible");
            }
        } else {
            channel.send().setTopic(GeneralUtils.buildMessage(1, args.length, args));
            saveTopic(channel, network);
        }
    }

    void saveTopic(Channel channel, PircBotX network) {
        DatabaseUtils.addChannelProperty(IRCUtils.getNetworkNameByNetwork(network), channel.getName(), "topic", channel.getTopic());
    }
}
