/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.global;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GloCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;


/**
 * @author jztech101
 */
@CMD
@GloCMD
public class Join extends GenericCommand {

    public Join() {
        super(GeneralUtils.toArray("join jo"), 20, "join [channel]","join a channel (not saved)");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        Bot.sendIRC().joinChannel(args[0]);
    }
}
