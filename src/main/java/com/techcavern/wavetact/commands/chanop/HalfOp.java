/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.chanop;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.GetUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * @author jztech101
 */
public class HalfOp extends GenericCommand {
    @CMD
    public HalfOp() {
        super(GeneralUtils.toArray("halfop hop"), 9, "halfop (-)(user to  halfop)");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate,int UserPermLevel, String... args) throws Exception {
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
    }
}
