/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.chanop;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.ChanOPCMD;
import com.techcavern.wavetact.utils.ErrorUtils;
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
@ChanOPCMD
public class HalfOp extends IRCCommand {

    public HalfOp() {
        super(GeneralUtils.toArray("halfop hop"), 10, "halfop (-)(user to  halfop)", "Sets half op mode on a user if it exists", true);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        if (network.getServerInfo().getPrefixes().contains("h")) {
            if (args.length >= 1) {
                if (args[0].equalsIgnoreCase("-")) {
                    channel.send().deHalfOp(user);
                } else if (args[0].startsWith("-")) {
                    channel.send().deHalfOp(GetUtils.getUserByNick(network, args[0].replaceFirst("-", "")));
                } else {
                    channel.send().halfOp(GetUtils.getUserByNick(network, args[0]));

                }
            } else {
                channel.send().halfOp(user);
            }
        } else {
            ErrorUtils.sendError(user, "This server does not support half ops");
        }
    }
}
