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
import org.pircbotz.Channel;
import org.pircbotz.PircBotZ;
import org.pircbotz.User;

/**
 * @author jztech101
 */
public class Disconnect extends GenericCommand {
    @CMD
    @GloCMD
    public Disconnect() {
        super(GeneralUtils.toArray("disconnect dc"), 20, "disconnect");
    }

    @Override
    public void onCommand(User user, PircBotZ Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        Bot.stopBotReconnect();
        Bot.sendIRC().quitServer();
    }
}
