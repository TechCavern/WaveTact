/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.chanowner;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.ChanOWNCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;


/**
 * @author jztech101
 */
@CMD
@ChanOWNCMD
public class Protect extends GenericCommand {

    public Protect() {
        super(GeneralUtils.toArray("protect prot sop"), 15, "protect (-)(User)", "sets protect mode if it exists on a user", true);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        if (network.getServerInfo().getPrefixes().contains("a")) {
            if (args.length >= 1) {
                if (args[0].equalsIgnoreCase("-")) {
                    channel.send().deSuperOp(user);
                } else if (args[0].startsWith("-")) {
                    channel.send().deSuperOp(GetUtils.getUserByNick(network, args[0].replaceFirst("-", "")));
                } else {
                    channel.send().superOp(GetUtils.getUserByNick(network, args[0]));

                }
            } else {
                channel.send().superOp(user);
            }
        } else {
            IRCUtils.sendError(user, "This server does not support SuperOps");
        }
    }
}

