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
        super(GeneralUtils.toArray("voice vop devoice devop"), 7, "voice (user)", "Voices a user", true);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        if (args.length >= 1) {
            user = IRCUtils.getUserByNick(network, args[0]);
        }
        if (command.contains("de")) {
            channel.send().deVoice(user);
        } else {
            channel.send().voice(user);
        }
    }
}
