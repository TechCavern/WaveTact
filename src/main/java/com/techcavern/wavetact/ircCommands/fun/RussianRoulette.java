/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.ircCommands.fun;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.Registry;
import org.apache.commons.lang3.RandomUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

/**
 * @author jztech101
 */
@IRCCMD
public class RussianRoulette extends IRCCommand {

    public RussianRoulette() {
        super(GeneralUtils.toArray("russianroulette roulette rr"), 0, "russianroulette", "Russian Roulette", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
       int x = Registry.randNum.nextInt(3);
        if(x == 2) {
            if (IRCUtils.checkIfCanKick(channel, network, user)) {
                IRCUtils.sendKick(network.getUserBot(), user, network, channel, "BANG!");
            } else {
                IRCUtils.sendAction(user, network, channel, "kicks " + IRCUtils.noPing(user.getNick()) + " (BANG!)", prefix);
            }
        }else{
            IRCUtils.sendMessage(user, network, channel, "Click!", prefix);
        }
    }
}
