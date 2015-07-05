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
public class Protect extends IRCCommand {

    public Protect() {
        super(GeneralUtils.toArray("protect prot sop desop deprotect"), 15, "protect (-)(user)", "Sets protect mode if it exists on a user", true);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        if (network.getServerInfo().getPrefixes().contains("a")) {
            if (args.length >= 1) {
                if (args[0].equalsIgnoreCase("-")) {
                    channel.send().deSuperOp(user);
                } else if (args[0].startsWith("-") || command.equalsIgnoreCase("deprotect") || command.equalsIgnoreCase("desop")) {
                    channel.send().deSuperOp(IRCUtils.getUserByNick(network, args[0].replaceFirst("-", "")));
                } else {
                    channel.send().superOp(IRCUtils.getUserByNick(network, args[0]));

                }
            } else if(command.equalsIgnoreCase("deprotect") || command.equalsIgnoreCase("desop")){
                channel.send().deSuperOp(user);
            }else {
                channel.send().superOp(user);
            }
        } else {
            ErrorUtils.sendError(user, "This server does not support protected ops");
        }
    }
}

