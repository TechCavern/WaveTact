/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.chanhalfop;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.ChanHOPCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.pircbotz.Channel;
import org.pircbotz.PircBotZ;
import org.pircbotz.User;

/**
 * @author jztech101
 */
public class Invite extends GenericCommand {
    @CMD
    @ChanHOPCMD
    public Invite() {
        super(GeneralUtils.toArray("invite inv"), 6, "invite (channel) [user]");
    }

    @Override
    public void onCommand(User user, PircBotZ Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        Bot.sendRaw().rawLine("INVITE " + args[0] + " " + channel.getName());
        channel.send().notice(args[0] + "invited by " + user.getNick());

    }
}
