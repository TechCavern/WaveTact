/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.ircCommands.chanop;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.annot.ChanOPCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.objects.IRCCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

/**
 * @author jztech101
 */
@IRCCMD
@ChanOPCMD
public class Op extends IRCCommand {

    public Op() {
        super(GeneralUtils.toArray("op aop"), 10, "op (-)(user to op)", "Ops a user", true);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("-")) {
                channel.send().deOp(user);
            } else if (args[0].startsWith("-")) {
                channel.send().deOp(GetUtils.getUserByNick(network, args[0].replaceFirst("-", "")));
            } else {
                channel.send().op(GetUtils.getUserByNick(network, args[0]));

            }
        } else {
            channel.send().op(user);
        }
    }
}
