/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.ircCommands.utils;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.*;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Record;
import org.pircbotx.Channel;
import static com.techcavern.wavetactdb.Tables.*;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

/**
 * @author jztech101
 */
@IRCCMD
public class CustomCommand extends IRCCommand {


    public CustomCommand() {
        super(GeneralUtils.toArray("customcommand ccmd cmsg cact"), 0, "customcommand (.) (a) (+/-)[command] [permlevel] [response]", "Responses may contain $1, $2, etc which indicate the argument separated by a space. $* indicates all remaining arguments.", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        String chan = null;
        String net = null;
        boolean isAction = false;
        boolean modify = false;
        boolean remove = false;
        for(int i = 0; i<1; i++){
            if (args[i].equalsIgnoreCase(".") && !GeneralUtils.isInteger(args[i+1])) {
                net = IRCUtils.getNetworkNameByNetwork(network);
                chan = channel.getName();
                args = ArrayUtils.remove(args, i);
                i--;
            } else if (args[i].equalsIgnoreCase("a") && !GeneralUtils.isInteger(args[i+1])) {
                isAction = true;
                args = ArrayUtils.remove(args, i);
                i--;
            }
        }
        String command;
        if(args[0].startsWith("+")){
            command = args[0].replaceFirst("\\+", "");
            modify = true;
        }else if(args[0].startsWith("-")){
            command = args[0].replaceFirst("\\-", "");
            remove = true;
        }else{
            command = args[0];
        }
        Record customCommand= DatabaseUtils.getCustomCommand(net, chan, command);
        if(modify && customCommand != null && userPermLevel >= customCommand.getValue(CUSTOMCOMMANDS.PERMLEVEL) && !customCommand.getValue(CUSTOMCOMMANDS.ISLOCKED)) {
            customCommand.setValue(CUSTOMCOMMANDS.PERMLEVEL, Integer.parseInt(args[1]));
            customCommand.setValue(CUSTOMCOMMANDS.VALUE, GeneralUtils.buildMessage(2, args.length, args).replace("\n", " "));
            DatabaseUtils.updateCustomCommand(customCommand);
        }else if(remove && customCommand != null && userPermLevel >= customCommand.getValue(CUSTOMCOMMANDS.PERMLEVEL) && !customCommand.getValue(CUSTOMCOMMANDS.ISLOCKED)){
            DatabaseUtils.removeCustomCommand(net, chan, command);
        }else if(customCommand == null && IRCUtils.getGenericCommand(command) == null){
            DatabaseUtils.addCustomCommand(net, chan, command, Integer.parseInt(args[1]), GeneralUtils.buildMessage(2, args.length, args).replace("\n", " "), false, isAction);
        }else{
            ErrorUtils.sendError(user, "Command already exists (If you were adding) or Command does not exist, or The command is locked (Either could be the problem if you were modifing)");
        }
    }

}

