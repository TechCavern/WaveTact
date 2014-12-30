/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.ircCommands.chanhalfop;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.ChanHOPCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.utils.objects.IRCCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

/**
 * @author jztech101
 */
@CMD
@ChanHOPCMD
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
        channel.send().kick(GetUtils.getUserByNick(network, args[0]), message);
    }

}
