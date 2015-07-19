/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.ircCommands.chanhalfop;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.DatabaseUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Record;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static com.techcavern.wavetactdb.Tables.CHANNELPROPERTY;

/**
 * @author jztech101
 */
@IRCCMD

public class Topic extends IRCCommand {

    public Topic() {
        super(GeneralUtils.toArray("topic"), 7, "Topic [a(add)/sw(switch)/+[topic #]/-[topic #]/(insert message to replace whole topic)/ss(switch separator)/r(revert)] (messages to add)(integer to swap)(separator to change to) (integer to swap)", "Manages the topic", true);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        Record topicseparator = DatabaseUtils.getChannelProperty(IRCUtils.getNetworkNameByNetwork(network), channel.getName(), "topicseparator");
        if (topicseparator == null) {
            IRCUtils.sendError(user, "Please set the topic separator before using this command");
            return;
        }
        StringUtils.split(channel.getTopic(), topicseparator.getValue(CHANNELPROPERTY.VALUE));
        List<String> topic = new LinkedList(Arrays.asList(StringUtils.split(channel.getTopic(), topicseparator.getValue(CHANNELPROPERTY.VALUE))));
        List<String> newtopic = new LinkedList(Arrays.asList(StringUtils.split(channel.getTopic(), topicseparator.getValue(CHANNELPROPERTY.VALUE))));
        if (args[0].equalsIgnoreCase("a") || args[0].equalsIgnoreCase("add")) {
            channel.send().setTopic(channel.getTopic() + " " + topicseparator.getValue(CHANNELPROPERTY.VALUE) + " " + GeneralUtils.buildMessage(1, args.length, args));
            saveTopic(channel, network);
        } else if (args[0].startsWith("+")) {
            newtopic.set(Integer.parseInt(args[0].replaceFirst("\\+", "")) - 1, " " + GeneralUtils.buildMessage(1, args.length, args) + " ");
            channel.send().setTopic(StringUtils.join(newtopic, topicseparator.getValue(CHANNELPROPERTY.VALUE)));
            saveTopic(channel, network);
        } else if (args[0].startsWith("-")) {
            newtopic.remove(Integer.parseInt(args[0].replaceFirst("\\-", "")) - 1);
            channel.send().setTopic(StringUtils.join(newtopic, topicseparator.getValue(CHANNELPROPERTY.VALUE)));
            saveTopic(channel, network);
        } else if (args[0].equalsIgnoreCase("sw") || args[0].equalsIgnoreCase("swap") || args[0].equalsIgnoreCase("switch")) {
            newtopic.set((Integer.parseInt(args[1]) - 1), topic.get(Integer.parseInt(args[2]) - 1));
            newtopic.set((Integer.parseInt(args[2]) - 1), topic.get(Integer.parseInt(args[1]) - 1));
            channel.send().setTopic(StringUtils.join(newtopic, topicseparator.getValue(CHANNELPROPERTY.VALUE)));
            saveTopic(channel, network);
        } else if (args[0].equalsIgnoreCase("r") || args[0].equalsIgnoreCase("revert")) {
            Record oldTopic = DatabaseUtils.getChannelProperty(IRCUtils.getNetworkNameByNetwork(network), channel.getName(), "topic");
            if (oldTopic != null) {
                channel.send().setTopic(oldTopic.getValue(CHANNELPROPERTY.VALUE));
            } else {
                IRCUtils.sendError(user, "Error: no reversal possible");
            }
        } else {
            channel.send().setTopic(GeneralUtils.buildMessage(0, args.length, args));
            saveTopic(channel, network);
        }
    }

    void saveTopic(Channel channel, PircBotX network) {
        Record topic = DatabaseUtils.getChannelProperty(IRCUtils.getNetworkNameByNetwork(network), channel.getName(), "topic");
        if (topic == null) {
            DatabaseUtils.addChannelProperty(IRCUtils.getNetworkNameByNetwork(network), channel.getName(), "topic", channel.getTopic());
        } else {
            topic.setValue(CHANNELPROPERTY.VALUE, channel.getTopic());
            DatabaseUtils.updateChannelProperty(topic);
        }
    }
}
