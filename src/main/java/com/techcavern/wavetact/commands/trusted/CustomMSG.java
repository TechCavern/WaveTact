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
        super(GeneralUtils.toArray("custommessage cmsg custommsg customsg"), 5, "custommessage (+/-)[command] [permlevel] [response]", "Responses may contain $1, $2, etc which indicate the argument separated by a space. $* indicates all remaining arguments.", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        if (args[0].toLowerCase().equalsIgnoreCase("list")) {
            List<String> SimpleCommands = GetUtils.getMessagesList(userPermLevel);
            IRCUtils.sendMessage(user, network, channel, StringUtils.join(SimpleCommands, ", "), prefix);
        } else {
            String command = args[0];
            if (args[0].startsWith("-")) {
                command = command.replaceFirst("\\-", "");
                SimpleMessage cmd = SimpleMessageUtils.getSimpleMessage(command);
                if (cmd.getPermLevel() <= userPermLevel) {
                    if (cmd == null) {
                        ErrorUtils.sendError(user, "Custom message does not exist");
                    } else if (cmd.getLockedStatus()) {
                        ErrorUtils.sendError(user, "Custom message is locked");
                    } else {
                        Constants.SimpleMessages.remove(cmd);
                        Constants.AllCommands.remove(cmd);
                        SimpleMessageUtils.saveSimpleMessages();
                        IRCUtils.sendMessage(user, network, channel, "Custom message removed", prefix);
                    }
                } else {
                    ErrorUtils.sendError(user, "Permission denied");
                }
            } else {
                int accessLevel = Integer.parseInt(args[1]);
                String msg = GeneralUtils.buildMessage(2, args.length, args).replace("\n", " ");
                if (args[0].startsWith("+")) {
                    command = command.replaceFirst("\\+", "");
                    SimpleMessage cmd = SimpleMessageUtils.getSimpleMessage(command);
                    if (cmd.getPermLevel() <= userPermLevel) {
                        if (cmd != null && !cmd.getLockedStatus()) {
                            Constants.SimpleMessages.remove(cmd);
                            Constants.AllCommands.remove(cmd);
                            Constants.SimpleMessages.add(new SimpleMessage(command, accessLevel, msg, false));
                            SimpleMessageUtils.saveSimpleMessages();
                            IRCUtils.sendMessage(user, network, channel, "Custom message modified", prefix);
                        } else if (cmd.getLockedStatus()) {
                            ErrorUtils.sendError(user, "Custom message locked");
                        } else {
                            ErrorUtils.sendError(user, "Custom Message does not exist");
                        }
                    } else {
                        ErrorUtils.sendError(user, "Permission denied");
                    }
                } else {
                    if (GetUtils.getCommand(command) != null) {
                        ErrorUtils.sendError(user, "Command already exists");
                        return;
                    } else
                        Constants.SimpleMessages.add(new SimpleMessage(command, accessLevel, msg, false));
                    SimpleMessageUtils.saveSimpleMessages();
                    IRCUtils.sendMessage(user, network, channel, "Custom message added", prefix);
                }
            }

        }
    }
}

