/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.chanhalfop;

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
public class Part extends GenericCommand {

    @CMD
    public Part() {
        super(GeneralUtils.toArray("part pa"), 7, "part [channel]");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate,int UserPermLevel, String... args) throws Exception {
       channel.send().part();
    }
}
