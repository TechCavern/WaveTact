/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.ircCommands.netadmin;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.annot.NAdmCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.objects.IRCCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

/**
 * @author jztech101
 */
@IRCCMD
@NAdmCMD
public class Disconnect extends IRCCommand {

    public Disconnect() {
        super(GeneralUtils.toArray("disconnect dc"), 20, "disconnect (reason)", "Disconnects from the network", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        network.sendIRC().quitServer(GeneralUtils.buildMessage(1, args.length, args));
        network.stopBotReconnect();
    }
}
