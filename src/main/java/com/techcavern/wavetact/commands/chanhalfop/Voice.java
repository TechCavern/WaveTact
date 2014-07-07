/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.chanhalfop;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.GetUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;


/**
 * @author jztech101
 */
public class Voice extends GenericCommand {
    @CMD
    public Voice() {
        super(GeneralUtils.toArray("voice vo vop"), 6, "Voice (-)(User)");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, String... args) throws Exception {
        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("-")) {
                channel.send().deVoice(user);
            } else if (args[0].startsWith("-")) {
                channel.send().deVoice(GetUtils.getUserByNick(channel, args[0].replaceFirst("-", "")));
            } else {
                channel.send().voice(GetUtils.getUserByNick(channel, args[0]));

            }
        } else {
            channel.send().voice(user);
        }
    }
}
