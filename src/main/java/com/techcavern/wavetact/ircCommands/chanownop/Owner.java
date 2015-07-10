/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.ircCommands.chanownop;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

/**
 * @author jztech101
 */
@IRCCMD
public class Owner extends IRCCommand {

    public Owner() {
        super(GeneralUtils.toArray("owner own oop deowner deoop"), 15, "owner (user)", "Sets owner mode if it exists on a user", true);
    }

    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        if (network.getServerInfo().getPrefixes().contains("q")) {
            if (args.length >= 1) {
                user = IRCUtils.getUserByNick(network, args[0]);
            }
            if (command.contains("de")) {
                channel.send().deOwner(user);
            } else {
                channel.send().owner(user);
            }
        } else {
            ErrorUtils.sendError(user, "This server does not support owners");
        }
    }
}

