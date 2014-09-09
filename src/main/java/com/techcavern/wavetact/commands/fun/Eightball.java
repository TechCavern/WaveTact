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
public class Eightball extends GenericCommand {

    public Eightball() {
        super(GeneralUtils.toArray("eightball 8ball 8b"), 0, "eightball [question]", "eightball");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        int randomint = RandomUtils.nextInt(0, GeneralRegistry.Eightball.size());
        IRCUtils.sendMessage(user, channel, GeneralRegistry.Eightball.get(randomint), isPrivate);
    }
}
