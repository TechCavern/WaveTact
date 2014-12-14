/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.trusted;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.TruCMD;
import com.techcavern.wavetact.utils.*;
import com.techcavern.wavetact.utils.databaseUtils.SimpleMessageUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import com.techcavern.wavetact.utils.objects.SimpleMessage;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.List;

/**
 * @author jztech101
 */
@CMD
@TruCMD
public class CustomMSG extends GenericCommand {

    public CustomMSG() {
        super(GeneralUtils.toArray("custommessage cmsg custommsg customsg"), 5, "custommessage (+/-)[Command] [permlevel] [Response]", "Responses may contain $1, $2, etc which indicate the argument separated by a space. $* indicates all remaining arguments.", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        if (args[0].toLowerCase().equalsIgnoreCase("list")) {
            List<String> SimpleCommands = GetUtils.getMessagesList(userPermLevel);
            IRCUtils.sendMessage(user, network, channel, StringUtils.join(SimpleCommands, ", "), prefix);
        } else if (args[0].startsWith("-")) {
            removeCommand(user, network, channel, isPrivate, userPermLevel, args[0].substring(1), prefix);
        } else if (args[0].startsWith("+")) {
            modifyCommand(user, network, channel, Integer.parseInt(args[1]), isPrivate, args[0].substring(1), userPermLevel, GeneralUtils.buildMessage(2, args.length, args).replace("\n", " "), prefix);
        } else {
            addCommand(user, network, channel, Integer.parseInt(args[1]), isPrivate, args[0], GeneralUtils.buildMessage(2, args.length, args).replace("\n", " "), prefix);
        }

    }


    private void addCommand(User user, PircBotX network, Channel channel, int accessLevel, boolean isPrivate, String cmd, String msg, String prefix) {
        if (GetUtils.getCommand(cmd) != null) {
            ErrorUtils.sendError(user, "Custom Message already exists");
            return;
        } else
            Constants.SimpleMessages.add(new SimpleMessage(cmd, accessLevel, msg, false));
        SimpleMessageUtils.saveSimpleMessages();
        IRCUtils.sendMessage(user, network, channel, "Custom Message added", prefix);
    }


    @SuppressWarnings("SuspiciousMethodCalls")
    private void modifyCommand(User user, PircBotX network, Channel channel, int accessLevel, boolean isPrivate, String command, int UserPermLevel, String msg, String prefix) {
        SimpleMessage cmd = SimpleMessageUtils.getSimpleMessage(command);
        if (cmd.getPermLevel() <= UserPermLevel) {
            if (cmd != null && !cmd.getLockedStatus()) {
                Constants.SimpleMessages.remove(cmd);
                Constants.AllCommands.remove(cmd);
                Constants.SimpleMessages.add(new SimpleMessage(command, accessLevel, msg, false));
                SimpleMessageUtils.saveSimpleMessages();
                IRCUtils.sendMessage(user, network, channel, "Custom Message modified", prefix);
            } else if (cmd.getLockedStatus()) {
                ErrorUtils.sendError(user, "Custom Message Locked");
            } else {
                ErrorUtils.sendError(user, "Custom Message Does Not Exist");
            }
        } else {
            ErrorUtils.sendError(user, "Permission Denied");
        }
    }

    @SuppressWarnings({"SuspiciousMethodCalls", "ConstantConditions"})
    private void removeCommand(User user, PircBotX network, Channel channel, boolean isPrivate, int UserPermLevel, String command, String prefix) {
        SimpleMessage cmd = SimpleMessageUtils.getSimpleMessage(command);
        if (cmd.getPermLevel() <= UserPermLevel) {
            if (cmd == null) {
                ErrorUtils.sendError(user, "Custom Message does not exist");
            } else if (cmd.getLockedStatus()) {
                ErrorUtils.sendError(user, "Custom Message is locked");
            } else {
                Constants.SimpleMessages.remove(cmd);
                Constants.AllCommands.remove(cmd);
                SimpleMessageUtils.saveSimpleMessages();
                IRCUtils.sendMessage(user, network, channel, "Custom Message removed", prefix);
            }
        } else {
            ErrorUtils.sendError(user, "Permission Denied");
        }
    }
}

