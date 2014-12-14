/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.fun;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GenCMD;
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
@CMD
@GenCMD
public class Attack extends GenericCommand {

    public Attack() {
        super(GeneralUtils.toArray("attack shoot"), 0, "attacks [something]", "attacks a user", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        String Something = GeneralUtils.buildMessage(0, args.length, args);
        if (Something.toLowerCase().equalsIgnoreCase(network.getUserBot().getNick())) {
            Something = user.getNick();
        }
        int randomint = RandomUtils.nextInt(0, GeneralRegistry.Attacks.size());
        FunObject attack = GeneralRegistry.Attacks.get(randomint);
        if (attack.getMessageExists()) {
            IRCUtils.sendAction(user, network, channel, attack.getAction().replace("$*", Something), prefix);
            IRCUtils.sendMessage(user, network, channel, attack.getMessage().replace("$*", Something), prefix);
        } else {
            IRCUtils.sendAction(user, network, channel, attack.getAction().replace("$*", Something), prefix);
        }
    }
}
