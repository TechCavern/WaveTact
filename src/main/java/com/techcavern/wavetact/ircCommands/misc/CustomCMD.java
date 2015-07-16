/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.ircCommands.misc;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.DatabaseUtils;
import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.jooq.Record;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import static com.techcavern.wavetactdb.Tables.CUSTOMCOMMANDS;

/**
 * @author jztech101
 */
@IRCCMD
public class CustomCMD extends IRCCommand {


    public CustomCMD() {
        super(GeneralUtils.toArray("custommsg cmsg cact customact"), 1, "custommsg (.) (+)(-)[command] [permlevel] [response]", "Responses may contain $1, $2, etc which indicate the argument separated by a space. $* indicates all remaining arguments.", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        String chan = null;
        String net = null;
        boolean isAction = false;
        boolean isModify = false;
        boolean isDelete = false;
        if (args[0].equalsIgnoreCase(".")) {
            net = IRCUtils.getNetworkNameByNetwork(network);
            chan = channel.getName();
            args = ArrayUtils.remove(args, 0);
        }
        if (command.equalsIgnoreCase("cact") || command.equalsIgnoreCase("customact")) {
            isAction = true;
        }
        String cCommand;
        if (args[0].startsWith("+")) {
            cCommand = args[0].replaceFirst("\\+", "");
            isModify = true;
        } else if (args[0].startsWith("-")) {
            cCommand = args[0].replaceFirst("\\-", "");
            isDelete = true;
        } else {
            cCommand = args[0];
        }
        Record customCommand = DatabaseUtils.getChannelCustomCommand(net, chan, cCommand);
        if (isModify && customCommand != null && userPermLevel >= customCommand.getValue(CUSTOMCOMMANDS.PERMLEVEL) && !customCommand.getValue(CUSTOMCOMMANDS.ISLOCKED)) {
            customCommand.setValue(CUSTOMCOMMANDS.PERMLEVEL, Integer.parseInt(args[1]));
            customCommand.setValue(CUSTOMCOMMANDS.ISACTION, isAction);
            customCommand.setValue(CUSTOMCOMMANDS.VALUE, GeneralUtils.buildMessage(2, args.length, args).replace("\n", " "));
            DatabaseUtils.updateCustomCommand(customCommand);
            IRCUtils.sendMessage(user, network, channel, "Custom Command modified", prefix);
        } else if (isDelete && customCommand != null && userPermLevel >= customCommand.getValue(CUSTOMCOMMANDS.PERMLEVEL) && !customCommand.getValue(CUSTOMCOMMANDS.ISLOCKED)) {
            DatabaseUtils.removeCustomCommand(net, chan, cCommand);
            IRCUtils.sendMessage(user, network, channel, "Custom Command removed", prefix);
        } else if (customCommand == null && IRCUtils.getCommand(cCommand, net, chan) == null && !isDelete && !isModify) {
            DatabaseUtils.addCustomCommand(net, chan, cCommand, Integer.parseInt(args[1]), GeneralUtils.buildMessage(2, args.length, args).replace("\n", " "), false, isAction);
            IRCUtils.sendMessage(user, network, channel, "Custom Command added", prefix);
        } else {
            ErrorUtils.sendError(user, "Command already exists (If you were adding) or Command does not exist, or The command is locked (Either could be the problem if you were modifying)");
        }
    }

}

