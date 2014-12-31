/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.ircCommands.netadmin;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.annot.NAdmCMD;
import com.techcavern.wavetact.utils.Registry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.fileUtils.Configuration;
import com.techcavern.wavetact.objects.IRCCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;


/**
 * @author jztech101
 */
@IRCCMD
@NAdmCMD
public class Join extends IRCCommand {

    public Join() {
        super(GeneralUtils.toArray("join jo"), 20, "join (+)[channel]", "Joins a channel", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        boolean permanent = false;
        if(args[0].startsWith("+")) {
            args[0] = args[0].replace("+", "");
            permanent = true;
        }
        if(permanent){
            Configuration config = Registry.configs.get(IRCUtils.getNetworkNameByNetwork(network));
            config.set("channels", config.getString("channels") + ", " + args[0]);
            config.save();
        }
        network.sendIRC().joinChannel(args[0]);
    }
}
