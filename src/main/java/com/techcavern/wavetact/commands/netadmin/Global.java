/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.netadmin;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.NAdmCMD;
import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.IRCCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;


/**
 * @author jztech101
 */
@CMD
@NAdmCMD
public class Global extends IRCCommand {

    public Global() {
        super(GeneralUtils.toArray("global"), 20, "global [networkname/all]", "Sends a global to the network or to all networks", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        if (!args[0].equalsIgnoreCase("all")) {
            PircBotX workingnetwork = GetUtils.getBotByNetworkName(args[0]);
            if (workingnetwork == null) {
                ErrorUtils.sendError(user, "Network does not exist");
                return;
            }
            if (workingnetwork == network) {
                IRCUtils.sendNetworkGlobal(GeneralUtils.buildMessage(1, args.length, args), workingnetwork, user);
            } else {
                if (userPermLevel >= 9001) {
                    IRCUtils.sendNetworkGlobal(GeneralUtils.buildMessage(1, args.length, args), workingnetwork, user);
                } else {
                    ErrorUtils.sendError(user, "Permission denied");
                }
            }
        } else if (args[0].equalsIgnoreCase("all") && userPermLevel >= 9001) {
            IRCUtils.sendGlobal(GeneralUtils.buildMessage(1, args.length, args), user);
        }
    }

}
