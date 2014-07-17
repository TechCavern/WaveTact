/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.global;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;


/**
 * @author jztech101
 */
public class Join extends GenericCommand {
    @CMD
    public Join() {
        super(GeneralUtils.toArray("join jo"), 20, "join [channel]");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, String... args)
            throws Exception {
        Bot.sendIRC().joinChannel(args[0]);
    }
}
