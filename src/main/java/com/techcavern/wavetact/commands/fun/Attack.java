/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.fun;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.apache.commons.lang3.RandomUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * @author jztech101
 */
public class Attack extends GenericCommand {

    @CMD
    public Attack() {
        super(GeneralUtils.toArray("attack shoot a s"), 0, "attacks [something]");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, String... args) throws Exception {
        String Something = GeneralUtils.buildMessage(0, args.length, args);
        int randomint = RandomUtils.nextInt(1 , 10);
        switch(randomint){
            case 1:
                IRCUtils.SendAction(user, channel, "sends a 53 inch monitor flying at " + Something, isPrivate);
                break;
            case 2:
                IRCUtils.SendAction(user, channel, "shoots a rocket at " + Something, isPrivate);
                break;
            case 3:
                IRCUtils.SendAction(user, channel, "punches "+Something+" right in the nuts", isPrivate);
                break;
            case 4:
                IRCUtils.SendAction(user, channel, "places a bomb near "+Something+" set for 2 seconds", isPrivate);
                IRCUtils.SendMessage(user, channel, "BANG", isPrivate);
                break;
            case 5:
                IRCUtils.SendAction(user, channel, "drops a 2000 pound object on "+Something, isPrivate);
                break;
            case 6:
                IRCUtils.SendAction(user, channel, "packs "+Something+" up and ships it to another galaxy", isPrivate);
                break;
            case 7:
                IRCUtils.SendAction(user, channel, "eats "+Something+" for breakfast", isPrivate);
                break;
            case 8:
                IRCUtils.SendAction(user, channel, "sends a flying desk at "+Something, isPrivate);
                break;
            case 9:
                IRCUtils.SendAction(user, channel, "swallows "+Something+" whole", isPrivate);
                break;
        }

    }
}
