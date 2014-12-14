/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.trusted;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.TruCMD;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.databaseUtils.SimpleActionUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import com.techcavern.wavetact.utils.objects.SimpleAction;
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
public class CustomACT extends GenericCommand {

    public CustomACT() {
        super(GeneralUtils.toArray("customaction cact customact"), 5, "customaction (+/-)[Command] [permlevel] [Response]", "Responses may contain $1, $2, etc which indicate the argument separated by a space. $* indicates all remaining arguments.", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        if (args[0].toLowerCase().equalsIgnoreCase("list")) {
            List<String> SimpleCommands = GetUtils.getActionsList(userPermLevel);
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
            IRCUtils.sendError(user, "Custom Action already exists");
            return;
        } else
            GeneralRegistry.SimpleActions.add(new SimpleAction(cmd, accessLevel, msg, false));
        SimpleActionUtils.saveSimpleActions();
        IRCUtils.sendMessage(user, network, channel, "Custom Action added", prefix);
    }


    @SuppressWarnings("SuspiciousMethodCalls")
    private void modifyCommand(User user, PircBotX network, Channel channel, int accessLevel, boolean isPrivate, String command, int UserPermLevel, String msg, String prefix) {
        SimpleAction cmd = SimpleActionUtils.getSimpleAction(command);
        if (cmd.getPermLevel() <= UserPermLevel) {
            if (cmd != null && !cmd.getLockedStatus()) {
                GeneralRegistry.SimpleActions.remove(cmd);
                GeneralRegistry.AllCommands.remove(cmd);
                GeneralRegistry.SimpleActions.add(new SimpleAction(command, accessLevel, msg, false));
                SimpleActionUtils.saveSimpleActions();
                IRCUtils.sendMessage(user, network, channel, "Custom Action modified", prefix);
            } else if (cmd.getLockedStatus()) {
                IRCUtils.sendError(user, "Custom Action Locked");
            } else {
                IRCUtils.sendError(user, "Custom Action Does Not Exist");
            }
        } else {
            IRCUtils.sendError(user, "Permission Denied");
        }
    }

    @SuppressWarnings({"SuspiciousMethodCalls", "ConstantConditions"})
    private void removeCommand(User user, PircBotX network, Channel channel, boolean isPrivate, int UserPermLevel, String command, String prefix) {
        SimpleAction cmd = SimpleActionUtils.getSimpleAction(command);
        if (cmd.getPermLevel() <= UserPermLevel) {
            if (cmd == null) {
                IRCUtils.sendError(user, "Custom Action does not exist");
            } else if (cmd.getLockedStatus()) {
                IRCUtils.sendError(user, "Custom Action is locked");
            } else {
                GeneralRegistry.SimpleActions.remove(cmd);
                GeneralRegistry.AllCommands.remove(cmd);
                SimpleActionUtils.saveSimpleActions();
                IRCUtils.sendMessage(user, network, channel, "Custom Action removed", prefix);
            }
        } else {
            IRCUtils.sendError(user, "Permission Denied");
        }
    }
}

