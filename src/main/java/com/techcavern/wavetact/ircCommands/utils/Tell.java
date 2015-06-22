package com.techcavern.wavetact.ircCommands.utils;

import com.google.gson.JsonObject;
import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@IRCCMD
public class Tell extends IRCCommand {

    public Tell() {
        super(GeneralUtils.toArray("tell"), 0, "tell [user] [message]", "Tells a user a message the next time they speak", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        String sender = PermUtils.authUser(network, user.getNick());
        if (sender == null) {
            ErrorUtils.sendError(user, "You must be identified");
            return;
        }
        String recipent = PermUtils.authUser(network, args[0]);
        if (recipent == null) {
            ErrorUtils.sendError(user, "Recipient must be identified");
            return;
        }

    }

}
