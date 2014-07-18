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
import org.pircbotx.UserLevel;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * @author jztech101
 */
public class Kick extends GenericCommand {
    @CMD
    public Kick() {
        super(GeneralUtils.toArray("kick k"), 6, "kick [user]");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate,int UserPermLevel, String... args) throws Exception {

        if (channel.getUserLevels(Bot.getUserBot()).contains(UserLevel.HALFOP) && !channel.isOwner(GetUtils.getUserByNick(Bot, args[0])) && !channel.isSuperOp(GetUtils.getUserByNick(Bot, args[0]))) {
            if (channel.isHalfOp(GetUtils.getUserByNick(Bot, args[0])) || channel.isOp(GetUtils.getUserByNick(Bot, args[0]))) {
                user.send().notice("Error: I must be at least opped to kick someone that is opped or halfopped");
            } else{
                channel.send().kick(GetUtils.getUserByNick(Bot, args[0]));
        }
        }else if (channel.getUserLevels(Bot.getUserBot()).contains(UserLevel.OP) && !channel.isOwner(GetUtils.getUserByNick(Bot, args[0])) && !channel.isSuperOp(GetUtils.getUserByNick(Bot, args[0]))) {
                channel.send().kick(GetUtils.getUserByNick(Bot, args[0]));

        } else if (channel.getUserLevels(Bot.getUserBot()).contains(UserLevel.OWNER)) {
                channel.send().kick(GetUtils.getUserByNick(Bot, args[0]));

        } else {
            user.send().notice("Error: I must be ownered in the channel to kick someone that is protected or ownered");
        }
    }

}
