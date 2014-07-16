/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.trusted;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.utils.*;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import com.techcavern.wavetact.utils.objects.CommandType;
import com.techcavern.wavetact.utils.databaseUtils.SimpleActionUtils;
import com.techcavern.wavetact.utils.databaseUtils.SimpleMessageUtils;
import com.techcavern.wavetact.utils.objects.SimpleAction;
import com.techcavern.wavetact.utils.objects.SimpleMessage;
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
        super(GeneralUtils.toArray("customcmd cmd custom cc"), 2, "customcmd [type(m/a)] (+/-)[Command] [permlevel] [Response] Responses may contain $1, $2, etc which indicate the argument separated by a space. $* indicates all remaining arguments.");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, String... args) throws Exception {

        switch (args[0].toLowerCase()) {
            case "m": // message
                if (args[1].startsWith("-")) {
                    removeCommand(Bot, user,channel, CommandType.MESSAGE, isPrivate,  args[1].substring(1));
                } else if (args[1].startsWith("+")) {
                    modifyCommand(user,channel, Bot, CommandType.MESSAGE, Integer.parseInt(args[2]),isPrivate, args[1].substring(1), GeneralUtils.buildMessage(3, args.length, args).replace("\n", " "));
                } else {
                    addCommand(user,channel, CommandType.MESSAGE, Integer.parseInt(args[2]), isPrivate, args[1], GeneralUtils.buildMessage(3, args.length, args).replace("\n", " "));
                }
                break;
            case "a": // action
                if (args[1].startsWith("-")) {
                    removeCommand(Bot, user,channel, CommandType.ACTION,isPrivate,  args[1].substring(1));
                } else if (args[1].startsWith("+")) {
                    modifyCommand(user,channel, Bot, CommandType.ACTION, Integer.parseInt(args[2]), isPrivate, args[1].substring(1), GeneralUtils.buildMessage(3, args.length, args).replace("\n"," "));
                } else {
                    addCommand(user,channel, CommandType.ACTION, Integer.parseInt(args[2]), isPrivate, args[1], GeneralUtils.buildMessage(3, args.length, args).replace("\n"," "));
                }
                break;
        }
    }


    private void addCommand(User user, Channel channel, CommandType type, int accessLevel, boolean isPrivate, String cmd, String msg) {
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
        IRCUtils.SendMessage(user, channel,"Command added", isPrivate);
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    private void modifyCommand(User user, Channel channel, PircBotX bot, CommandType type, int accessLevel,boolean isPrivate, String command, String msg) {
        GenericCommand cmd = null;
        if (type == CommandType.MESSAGE)
            cmd = SimpleMessageUtils.getSimpleMessage(command);
        else if (type == CommandType.ACTION)
            cmd = SimpleActionUtils.getSimpleAction(command);
        if(cmd.getPermLevel() <= PermUtils.getPermLevel(bot, user, channel)){
        if (cmd  != null && !cmd.getLockedStatus()) {
            GeneralRegistry.SimpleActions.remove(cmd);
            GeneralRegistry.SimpleMessages.remove(cmd);
        }else if(cmd.getLockedStatus()) {
            IRCUtils.SendMessage(user, channel, "Command Locked", isPrivate);

        } else{
            IRCUtils.SendMessage(user, channel, "Command Does Not Exist", isPrivate);

        }
        if (type == CommandType.ACTION) {
            GeneralRegistry.SimpleActions.add(new SimpleAction(command, accessLevel, msg, false));
            SimpleActionUtils.saveSimpleActions();
        } else if (type == CommandType.MESSAGE) {
            GeneralRegistry.SimpleMessages.add(new SimpleMessage(command, accessLevel, msg, false));
            SimpleMessageUtils.saveSimpleMessages();

        }
        IRCUtils.SendMessage(user, channel, "Command modified", isPrivate);
        }else{
            IRCUtils.SendMessage(user, channel, "Permission Denied", isPrivate);
        }
    }

    @SuppressWarnings({"SuspiciousMethodCalls", "ConstantConditions"})
    private void removeCommand(PircBotX bot, User user, Channel channel, CommandType type, boolean isPrivate, String command) {
        GenericCommand cmd = null;
        if (type == CommandType.MESSAGE)
            cmd = SimpleMessageUtils.getSimpleMessage(command);
        else if (type == CommandType.ACTION)
            cmd = SimpleActionUtils.getSimpleAction(command);
        if(cmd.getPermLevel() <= PermUtils.getPermLevel(bot, user, channel)){
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

            IRCUtils.SendMessage(user, channel, "Command removed", isPrivate);
        }
    }else{
            IRCUtils.SendMessage(user, channel, "Permission Denied", isPrivate);
        }
}}

