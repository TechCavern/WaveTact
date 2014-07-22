/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.chanop;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.ChanOPCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

/**
 * @author jztech101
 */
public class Mode extends GenericCommand {
    @CMD
    @ChanOPCMD

    public Mode() {
        super(GeneralUtils.toArray("mode mo"), 9, "mode (channel) [modes to set]");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        if (channel.isOp(Bot.getUserBot()) || channel.isSuperOp(Bot.getUserBot()) || channel.isOwner(Bot.getUserBot())) {
            channel.send().setMode(args[0]);
        } else {
            channel.send().message("Error I must be at least op to perform the operation requested.");
        }

    }
}
