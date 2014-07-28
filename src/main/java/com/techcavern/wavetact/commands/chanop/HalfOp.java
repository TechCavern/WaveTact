/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.chanop;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.ChanOPCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.pircbotz.Channel;
import org.pircbotz.PircBotZ;
import org.pircbotz.User;

/**
 * @author jztech101
 */
public class HalfOp extends GenericCommand {
    @CMD
    @ChanOPCMD

    public HalfOp() {
        super(GeneralUtils.toArray("halfop hop"), 9, "halfop (-)(user to  halfop)");
    }

    @Override
    public void onCommand(User user, PircBotZ Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        if (Bot.getServerInfo().getPrefixes().contains("h")) {
            if (args.length >= 1) {
                if (args[0].equalsIgnoreCase("-")) {
                    channel.send().deHalfOp(user);
                } else if (args[0].startsWith("-")) {
                    channel.send().deHalfOp(GetUtils.getUserByNick(Bot, args[0].replaceFirst("-", "")));
                } else {
                    channel.send().halfOp(GetUtils.getUserByNick(Bot, args[0]));

                }
            } else {
                channel.send().halfOp(user);
            }
        } else {
            user.send().notice("This server does not support HalfOps");
        }
    }
}
