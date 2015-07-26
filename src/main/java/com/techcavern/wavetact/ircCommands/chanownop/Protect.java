/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.ircCommands.chanownop;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;


/**
 * @author jztech101
 */
@IRCCMD
public class Protect extends IRCCommand {

    public Protect() {
        super(GeneralUtils.toArray("protect prot sop desop deprotect"), 15, "protect (user)", "Sets protect mode if it exists on a user", true);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        if (network.getServerInfo().getPrefixes().contains("a")) {
            String nick = user.getNick();
            if (args.length >= 1) {
                nick = args[0];
            }
            if (command.contains("de")) {
                IRCUtils.setMode(channel, network, "-ao", nick + " " + nick);
            } else {
                IRCUtils.setMode(channel, network, "+ao", nick + " " + nick);
            }
        } else {
            IRCUtils.sendError(user, network, channel, "This server does not support protected ops", prefix);
        }
    }
}

