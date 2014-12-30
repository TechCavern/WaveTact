/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.trusted;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.TruCMD;
import com.techcavern.wavetact.utils.*;
import com.techcavern.wavetact.utils.databaseUtils.SimpleActionUtils;
import com.techcavern.wavetact.utils.objects.IRCCommand;
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
public class CustomACT extends IRCCommand {

    public CustomACT() {
        super(GeneralUtils.toArray("customaction cact customact"), 5, "customaction (+/-)[command] [permlevel] [response]", "Responses may contain $1, $2, etc which indicate the argument separated by a space. $* indicates all remaining arguments.", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        if (args[0].toLowerCase().equalsIgnoreCase("list")) {
            List<String> SimpleCommands = GetUtils.getActionsList(userPermLevel);
            IRCUtils.sendMessage(user, network, channel, StringUtils.join(SimpleCommands, ", "), prefix);
        } else {
            String command = args[0];
            if (args[0].startsWith("-")) {
                command = command.replaceFirst("\\-", "");
                SimpleAction cmd = SimpleActionUtils.getSimpleAction(command);
                if (cmd.getPermLevel() <= userPermLevel) {
                    if (cmd == null) {
                        ErrorUtils.sendError(user, "Custom action does not exist");
                    } else if (cmd.getLockedStatus()) {
                        ErrorUtils.sendError(user, "Custom action is locked");
                    } else {
                        Registry.SimpleActions.remove(cmd);
                        Registry.AllCommands.remove(cmd);
                        SimpleActionUtils.saveSimpleActions();
                        IRCUtils.sendMessage(user, network, channel, "Custom action removed", prefix);
                    }
                } else {
                    ErrorUtils.sendError(user, "Permission denied");
                }
            } else {
                int accessLevel = Integer.parseInt(args[1]);
                String msg = GeneralUtils.buildMessage(2, args.length, args).replace("\n", " ");
                if (args[0].startsWith("+")) {
                    command = command.replaceFirst("\\+", "");
                    SimpleAction cmd = SimpleActionUtils.getSimpleAction(command);
                    if (cmd.getPermLevel() <= userPermLevel) {
                        if (cmd != null && !cmd.getLockedStatus()) {
                            Registry.SimpleActions.remove(cmd);
                            Registry.AllCommands.remove(cmd);
                            Registry.SimpleActions.add(new SimpleAction(command, accessLevel, msg, false));
                            SimpleActionUtils.saveSimpleActions();
                            IRCUtils.sendMessage(user, network, channel, "Custom action modified", prefix);
                        } else if (cmd.getLockedStatus()) {
                            ErrorUtils.sendError(user, "Custom action locked");
                        } else {
                            ErrorUtils.sendError(user, "Custom action does not exist");
                        }
                    } else {
                        ErrorUtils.sendError(user, "Permission denied");
                    }
                } else {
                    if (GetUtils.getGenericCommand(command) != null) {
                        ErrorUtils.sendError(user, "Command already exists");
                        return;
                    } else
                        Registry.SimpleActions.add(new SimpleAction(command, accessLevel, msg, false));
                    SimpleActionUtils.saveSimpleActions();
                    IRCUtils.sendMessage(user, network, channel, "Custom action added", prefix);
                }
            }
        }
    }

}

