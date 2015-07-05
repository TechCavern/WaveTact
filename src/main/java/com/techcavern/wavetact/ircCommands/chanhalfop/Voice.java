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

public class Voice extends IRCCommand {

    public Voice() {
        super(GeneralUtils.toArray("voice vop"), 7, "voice (-)(user)", "Voices a user", true);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("-")) {
                channel.send().deVoice(user);
            } else if (args[0].startsWith("-")) {
                channel.send().deVoice(IRCUtils.getUserByNick(network, args[0].replaceFirst("-", "")));
            } else {
                channel.send().voice(IRCUtils.getUserByNick(network, args[0]));

            }
        } else {
            channel.send().voice(user);
        }
    }
}
