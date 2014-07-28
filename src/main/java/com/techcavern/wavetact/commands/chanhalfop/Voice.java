/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.chanhalfop;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.ChanHOPCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.pircbotz.Channel;
import org.pircbotz.PircBotZ;
import org.pircbotz.User;


/**
 * @author jztech101
 */
public class Voice extends GenericCommand {
    @CMD
    @ChanHOPCMD

    public Voice() {
        super(GeneralUtils.toArray("voice vo vop"), 6, "Voice (-)(User)");
    }

    @Override
    public void onCommand(User user, PircBotZ Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("-")) {
                channel.send().deVoice(user);
            } else if (args[0].startsWith("-")) {
                channel.send().deVoice(GetUtils.getUserByNick(Bot, args[0].replaceFirst("-", "")));
            } else {
                channel.send().voice(GetUtils.getUserByNick(Bot, args[0]));

            }
        } else {
            channel.send().voice(user);
        }
    }
}
