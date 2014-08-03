/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.fun;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.FunCMD;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.FunObject;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.apache.commons.lang3.RandomUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

/**
 * @author jztech101
 */
public class Attack extends GenericCommand {

    @CMD
    @FunCMD
    public Attack() {
        super(GeneralUtils.toArray("attack shoot a s"), 0, "attacks [something]","attacks a user");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        String Something = GeneralUtils.buildMessage(0, args.length, args);
        if (Something.toLowerCase().equalsIgnoreCase(Bot.getUserBot().getNick())) {
            Something = user.getNick();
        }
        int randomint = RandomUtils.nextInt(0, GeneralRegistry.Attacks.size());
        FunObject attack = GeneralRegistry.Attacks.get(randomint);
        if (attack.getMessageExists()) {
            IRCUtils.SendAction(user, channel, attack.getAction().replace("$*", Something), isPrivate);
            IRCUtils.SendMessage(user, channel, attack.getMessage().replace("$*", Something), isPrivate);
        } else {
            IRCUtils.SendAction(user, channel, attack.getAction().replace("$*", Something), isPrivate);
        }
    }
}
