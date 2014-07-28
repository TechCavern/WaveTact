/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.fun;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.FunCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.pircbotz.Channel;
import org.pircbotz.PircBotZ;
import org.pircbotz.User;
import org.pircbotz.UserLevel;

/**
 * @author jztech101
 */
public class SomethingAwesome extends GenericCommand {

    @CMD
    @FunCMD

    public SomethingAwesome() {
        super(GeneralUtils.toArray("somethingawesome sa awesome something"), 0, "somethingawesome");
    }

    @Override
    public void onCommand(User user, PircBotZ Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        if (channel != null && channel.getUserLevels(Bot.getUserBot()).contains(UserLevel.OP) && !channel.isOwner(user) && !channel.isSuperOp(user)) {
            channel.send().kick(user, "http://bit.ly/1c9vo1S");
        } else if (channel != null && channel.getUserLevels(Bot.getUserBot()).contains(UserLevel.OWNER)) {
            channel.send().kick(user, "http://bit.ly/1c9vo1S");
        } else {
            IRCUtils.SendAction(user, channel, "kicks " + user.getNick() + "(http://bit.ly/1c9vo1S)", isPrivate);

        }
    }
}
