/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.chanhalfop;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.ChanHOPCMD;
import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.UserLevel;

/**
 * @author jztech101
 */
@CMD
@ChanHOPCMD
public class Kick extends GenericCommand {

    public Kick() {
        super(GeneralUtils.toArray("kick"), 7, "kick [user] (message)", "kicks a user with specified message or none", true);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        String message = "Kicked by " + user.getNick();
        if (args.length > 1) {
            message = "Kicked by " + user.getNick() + ": " + GeneralUtils.buildMessage(1, args.length, args);
        }
        if (channel.getUserLevels(network.getUserBot()).contains(UserLevel.HALFOP) && !channel.isOwner(GetUtils.getUserByNick(network, args[0])) && !channel.isSuperOp(GetUtils.getUserByNick(network, args[0]))) {
            if (channel.isHalfOp(GetUtils.getUserByNick(network, args[0])) || channel.isOp(GetUtils.getUserByNick(network, args[0]))) {
                ErrorUtils.sendError(user, "Error: I must be at least opped to kick someone that is opped or halfopped");
            } else {
                channel.send().kick(GetUtils.getUserByNick(network, args[0]), message);
            }
        } else if (channel.getUserLevels(network.getUserBot()).contains(UserLevel.OP) && !channel.isOwner(GetUtils.getUserByNick(network, args[0])) && !channel.isSuperOp(GetUtils.getUserByNick(network, args[0]))) {
            channel.send().kick(GetUtils.getUserByNick(network, args[0]), message);

        } else if (channel.getUserLevels(network.getUserBot()).contains(UserLevel.OWNER)) {
            channel.send().kick(GetUtils.getUserByNick(network, args[0]), message);

        } else {
            ErrorUtils.sendError(user, "Error: I must be ownered in the channel to kick someone that is protected or ownered");
        }
    }

}
