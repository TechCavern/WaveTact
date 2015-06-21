/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.ircCommands.chanhalfop;

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

public class Kick extends IRCCommand {

    public Kick() {
        super(GeneralUtils.toArray("kick"), 7, "kick [user] (message)", "Kicks a user with specified message or none", true);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        String message = "Kicked by " + user.getNick();
        if (args.length > 1) {
            message += ": " + GeneralUtils.buildMessage(1, args.length, args);
        }
        if (args[0].equalsIgnoreCase(network.getNick()))
            IRCUtils.sendKick(network.getUserBot(), user, network, channel, message);
        else
            IRCUtils.sendKick(network.getUserBot(), IRCUtils.getUserByNick(network, args[0]), network, channel, message);
    }

}
