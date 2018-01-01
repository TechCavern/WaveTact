/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.ircCommands.chanadmin;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.DatabaseUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.jooq.Record;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import static com.techcavern.wavetactdb.Tables.CUSTOMCOMMANDS;

@IRCCMD
public class LockCustomCMD extends IRCCommand {

    public LockCustomCMD() {
        super(GeneralUtils.toArray("lockcustomcmd lockccmd lccmd"), 18, "lockcustomcmd (-)[command]", "Locks/Unlocks a channel custom command", true);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        Record customCommand = DatabaseUtils.getCustomCommand(IRCUtils.getNetworkNameByNetwork(network), channel.getName(), args[0].replaceFirst("-", ""));
        if (customCommand != null) {
            if (args[0].startsWith("-")) {
                customCommand.setValue(CUSTOMCOMMANDS.ISLOCKED, false);
                IRCUtils.sendMessage(user, network, IRCUtils.getMsgChannel(channel, isPrivate), "Custom command unlocked", prefix);
            } else {
                customCommand.setValue(CUSTOMCOMMANDS.ISLOCKED, true);
                IRCUtils.sendMessage(user, network, IRCUtils.getMsgChannel(channel, isPrivate), "Custom command locked", prefix);
            }
            DatabaseUtils.updateCustomCommand(customCommand);
        }
    }
}
