package com.techcavern.wavetact.commands.utils;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;


public class Hostmask extends GenericCommand {
    @CMD
    public Hostmask() {
        super(GeneralUtils.toArray("hostmask host mask"), 0, "Hostmask $[nick]");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        if (IRCUtils.getHostmask(Bot, args[0].replaceFirst("\\$", ""), false) != null) {
            if (args[0].startsWith("$")) {
                IRCUtils.SendMessage(user, channel, IRCUtils.getHostmask(Bot, args[0].replaceFirst("\\$", ""), true), isPrivate);
            } else {
                IRCUtils.SendMessage(user, channel, IRCUtils.getHostmask(Bot, args[0], false), isPrivate);
            }
        } else {
            user.send().notice("User not found");
        }

    }
}
