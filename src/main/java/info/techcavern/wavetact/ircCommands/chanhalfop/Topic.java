/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.techcavern.wavetact.ircCommands.chanhalfop;

import info.techcavern.wavetact.annot.IRCCMD;
import info.techcavern.wavetact.objects.IRCCommand;
import info.techcavern.wavetact.utils.DatabaseUtils;
import info.techcavern.wavetact.utils.GeneralUtils;
import info.techcavern.wavetact.utils.IRCUtils;
import info.techcavern.wavetact.annot.IRCCMD;
import info.techcavern.wavetact.objects.IRCCommand;
import info.techcavern.wavetact.utils.DatabaseUtils;
import info.techcavern.wavetact.utils.GeneralUtils;
import info.techcavern.wavetact.utils.IRCUtils;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Record;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static info.techcavern.wavetactdb.Tables.CHANNELPROPERTY;

/**
 * @author jztech101
 */
@IRCCMD

public class Topic extends IRCCommand {

    public Topic() {
        super(GeneralUtils.toArray("topic"), 7, "Topic [ad(add)/sw(switch)/+[topic #]/-[topic #]/(insert message to replace whole topic)/ss(switch separator)/rev(revert)] (messages to add)(integer to swap)(separator to change to) (integer to swap)", "Manages the topic", true);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        Record topicseparator = DatabaseUtils.getChannelProperty(IRCUtils.getNetworkNameByNetwork(network), channel.getName(), "topicseparator");
        boolean isTopicCommand = args[0].equalsIgnoreCase("ad") || args[0].equalsIgnoreCase("add")||args[0].startsWith("+")||args[0].startsWith("-")||args[0].equalsIgnoreCase("sw") || args[0].equalsIgnoreCase("swap") || args[0].equalsIgnoreCase("switch") ||args[0].equalsIgnoreCase("rev") || args[0].equalsIgnoreCase("revert");
        if (!isTopicCommand) {
            IRCUtils.setTopic(network,channel,GeneralUtils.buildMessage(0, args.length, args));
            saveTopic(channel, network);
            return;
        } else if (topicseparator == null) {
            IRCUtils.sendError(user, network, channel, "Please set the topic separator before using this command", prefix);
            return;
        }
        StringUtils.split(channel.getTopic(), topicseparator.getValue(CHANNELPROPERTY.VALUE));
        List<String> topic = new LinkedList(Arrays.asList(StringUtils.split(channel.getTopic(), topicseparator.getValue(CHANNELPROPERTY.VALUE))));
        List<String> newtopic = new LinkedList(Arrays.asList(StringUtils.split(channel.getTopic(), topicseparator.getValue(CHANNELPROPERTY.VALUE))));
        if (args[0].equalsIgnoreCase("ad") || args[0].equalsIgnoreCase("add")) {
            IRCUtils.setTopic(network,channel,channel.getTopic() + " " + topicseparator.getValue(CHANNELPROPERTY.VALUE) + " " + GeneralUtils.buildMessage(1, args.length, args));
            saveTopic(channel, network);
        } else if (args[0].startsWith("+")) {
            newtopic.set(Integer.parseInt(args[0].replaceFirst("\\+", "")) - 1, " " + GeneralUtils.buildMessage(1, args.length, args) + " ");
            IRCUtils.setTopic(network,channel,StringUtils.join(newtopic, topicseparator.getValue(CHANNELPROPERTY.VALUE)));
            saveTopic(channel, network);
        } else if (args[0].startsWith("-")) {
            newtopic.remove(Integer.parseInt(args[0].replaceFirst("\\-", "")) - 1);
            IRCUtils.setTopic(network,channel,StringUtils.join(newtopic, topicseparator.getValue(CHANNELPROPERTY.VALUE)));
            saveTopic(channel, network);
        } else if (args[0].equalsIgnoreCase("sw") || args[0].equalsIgnoreCase("swap") || args[0].equalsIgnoreCase("switch")) {
            newtopic.set((Integer.parseInt(args[1]) - 1), topic.get(Integer.parseInt(args[2]) - 1));
            newtopic.set((Integer.parseInt(args[2]) - 1), topic.get(Integer.parseInt(args[1]) - 1));
            channel.send().setTopic(StringUtils.join(newtopic, topicseparator.getValue(CHANNELPROPERTY.VALUE)));
            saveTopic(channel, network);
        } else if (args[0].equalsIgnoreCase("rev") || args[0].equalsIgnoreCase("revert")) {
            Record oldTopic = DatabaseUtils.getChannelProperty(IRCUtils.getNetworkNameByNetwork(network), channel.getName(), "topic");
            if (oldTopic != null) {
                IRCUtils.setTopic(network,channel,oldTopic.getValue(CHANNELPROPERTY.VALUE));
            } else {
                IRCUtils.sendError(user, network, channel, "Error: no reversal possible", prefix);
            }
        } else {
           IRCUtils.setTopic(network,channel,GeneralUtils.buildMessage(0, args.length, args));
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
