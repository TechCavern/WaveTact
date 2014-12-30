package com.techcavern.wavetact.ircCommands.dnsinfo;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.IRCCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@CMD
@GenCMD
public class Hostmask extends IRCCommand {

    public Hostmask() {
        super(GeneralUtils.toArray("hostmask host"), 0, "hostmask (+)[nick]", "Gets the hostmask of a user - + before gets the ban mask of a user", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        if (IRCUtils.getHostmask(network, args[0].replaceFirst("\\$", ""), false) != null) {
            if (args[0].startsWith("$")) {
                IRCUtils.sendMessage(user, network, channel, IRCUtils.getHostmask(network, args[0].replaceFirst("\\$", ""), true), prefix);
            } else {
                IRCUtils.sendMessage(user, network, channel, IRCUtils.getHostmask(network, args[0], false), prefix);
            }
        } else {
            ErrorUtils.sendError(user, "User not found");
        }

    }
}
