package com.techcavern.wavetact.commands.dnsinfo;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@CMD
@GenCMD
public class Hostmask extends GenericCommand {

    public Hostmask() {
        super(GeneralUtils.toArray("hostmask host"), 0, "Hostmask $[nick]", "gets the hostmask of a user - $ before gets the ban mask of a user");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        if (IRCUtils.getHostmask(Bot, args[0].replaceFirst("\\$", ""), false) != null) {
            if (args[0].startsWith("$")) {
                IRCUtils.sendMessage(user, channel, IRCUtils.getHostmask(Bot, args[0].replaceFirst("\\$", ""), true), isPrivate);
            } else {
                IRCUtils.sendMessage(user, channel, IRCUtils.getHostmask(Bot, args[0], false), isPrivate);
            }
        } else {
            IRCUtils.sendError(user, "User not found");
        }

    }
}
