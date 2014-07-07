/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.trusted;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import com.techcavern.wavetact.utils.objects.CommandType;
import com.techcavern.wavetact.utils.databaseUtils.SimpleActionUtils;
import com.techcavern.wavetact.utils.databaseUtils.SimpleMessageUtils;
import com.techcavern.wavetact.utils.objects.SimpleAction;
import com.techcavern.wavetact.utils.objects.SimpleMessage;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.GetUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * @author jztech101
 */
public class CustomCMD extends GenericCommand {

    @CMD
    public CustomCMD() {
        super(GeneralUtils.toArray("customcmd cmd custom cc"), 5, "customcmd [type(m/a)] (+/-)[Command] [permlevel] [Response] Responses may contain $1, $2, etc which indicate the argument separated by a space. $* indicates all remaining arguments.");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, String... args) throws Exception {

        switch (args[0].toLowerCase()) {
            case "m": // message
                if (args[1].startsWith("-")) {
                    removeCommand(user,channel, CommandType.MESSAGE, args[1].substring(1));
                } else if (args[1].startsWith("+")) {
                    modifyCommand(user,channel, CommandType.MESSAGE, Integer.parseInt(args[2]), args[1].substring(1), GeneralUtils.buildMessage(3, args.length, args));
                } else {
                    addCommand(user,channel, CommandType.MESSAGE, Integer.parseInt(args[2]), args[1], GeneralUtils.buildMessage(3, args.length, args));
                }
                break;
            case "a": // action
                if (args[1].startsWith("-")) {
                    removeCommand(user,channel, CommandType.ACTION, args[1].substring(1));
                } else if (args[1].startsWith("+")) {
                    modifyCommand(user,channel, CommandType.ACTION, Integer.parseInt(args[2]), args[1].substring(1), GeneralUtils.buildMessage(3, args.length, args));
                } else {
                    addCommand(user,channel, CommandType.ACTION, Integer.parseInt(args[2]), args[1], GeneralUtils.buildMessage(3, args.length, args));
                }
                break;
        }
    }


    private void addCommand(User user, Channel channel, CommandType type, int accessLevel, String cmd, String msg) {
        if (GetUtils.getCommand(cmd) != null) {
            user.send().notice("Command already exists");
            return;
        }
        if (type == CommandType.ACTION) {
            GeneralRegistry.SimpleActions.add(new SimpleAction(cmd, accessLevel, msg, false));
            SimpleActionUtils.saveSimpleActions();
        } else if (type == CommandType.MESSAGE) {
            GeneralRegistry.SimpleMessages.add(new SimpleMessage(cmd, accessLevel, msg, false));
            SimpleMessageUtils.saveSimpleMessages();
        }
        IRCUtils.SendMessage(user, channel,"Command added");
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    private void modifyCommand(User user, Channel channel, CommandType type, int accessLevel, String command, String msg) {
        if (GetUtils.getCommand(command) != null) {
            GenericCommand cmd = GetUtils.getCommand(command);
            GeneralRegistry.GenericCommands.remove(cmd);
            GeneralRegistry.SimpleActions.remove(cmd);
            GeneralRegistry.SimpleMessages.remove(cmd);
        }

        if (type == CommandType.ACTION) {
            GeneralRegistry.SimpleActions.add(new SimpleAction(command, accessLevel, msg, false));
            SimpleActionUtils.saveSimpleActions();
        } else if (type == CommandType.MESSAGE) {
            GeneralRegistry.SimpleMessages.add(new SimpleMessage(command, accessLevel, msg, false));
            SimpleMessageUtils.saveSimpleMessages();

        }
        IRCUtils.SendMessage(user, channel, "Command modified");
    }

    @SuppressWarnings({"SuspiciousMethodCalls", "ConstantConditions"})
    private void removeCommand(User user, Channel channel, CommandType type, String command) {
        GenericCommand cmd = null;
        if (type == CommandType.MESSAGE)
            cmd = SimpleMessageUtils.getSimpleMessage(command);
        else if (type == CommandType.ACTION)
            cmd = SimpleActionUtils.getSimpleAction(command);

        if (cmd == null) {
            user.send().notice("Command does not exist");
        } else if (cmd.getLockedStatus()) {
            user.send().notice("Command is locked");
        } else {
            if (type == CommandType.MESSAGE) {
                GeneralRegistry.SimpleMessages.remove(cmd);
                SimpleMessageUtils.saveSimpleMessages();

            } else if (type == CommandType.ACTION) {
                GeneralRegistry.SimpleActions.remove(cmd);
                SimpleActionUtils.saveSimpleActions();
            }
            GeneralRegistry.GenericCommands.remove(cmd);

            IRCUtils.SendMessage(user, channel, "Command removed");
        }
    }
}
