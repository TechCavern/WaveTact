/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.ircCommands.anonymonity;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.annot.TruCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.PermUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

/**
 * @author jztech101
 */
@IRCCMD
@TruCMD
public class Say extends IRCCommand {

    public Say() {
        super(GeneralUtils.toArray("say msg"), 5, "say [something]", "Makes the bot say something", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        Channel chan;
        if (args.length > 1) {
            prefix = IRCUtils.getPrefix(network, args[0]);
            if (!prefix.isEmpty())
                chan = IRCUtils.getChannelbyName(network, args[0].replace(prefix, ""));
            else
                chan = IRCUtils.getChannelbyName(network, args[0]);
            if (chan != null)
                args = ArrayUtils.remove(args, 0);
            else
                chan = channel;
        } else {
            chan = channel;
        }
        if (PermUtils.getPermLevel(network, user.getNick(), chan) >= 5) {
            IRCUtils.sendMessage(user, network, chan, StringUtils.join(args, " ").replace("\n", " "), prefix);
        } else {
            ErrorUtils.sendError(user, "Permission denied");
        }
    }
}



