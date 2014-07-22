/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.chanowner;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

/**
 * @author jztech101
 */
public class Owner extends GenericCommand {
    @CMD
    public Owner() {
        super(GeneralUtils.toArray("owner own oop"), 14, "Owner (-)(User)");
    }

    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("-")) {
                channel.send().deOwner(user);
            } else if (args[0].startsWith("-")) {
                channel.send().deOwner(GetUtils.getUserByNick(Bot, args[0].replaceFirst("-", "")));
            } else {
                channel.send().owner(GetUtils.getUserByNick(Bot, args[0]));

            }
        } else {
            channel.send().owner(user);
        }
    }
}
