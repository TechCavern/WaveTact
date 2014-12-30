package com.techcavern.wavetact.ircCommands.auth;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.utils.*;
import com.techcavern.wavetact.objects.AuthedUser;
import com.techcavern.wavetact.objects.IRCCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@CMD
@GenCMD
public class Logout extends IRCCommand {

    public Logout() {
        super(GeneralUtils.toArray("logout"), 0, "logout", "Logs you out", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        AuthedUser authedUser = PermUtils.getAuthedUser(network, user.getNick());
        if (authedUser == null) {
            ErrorUtils.sendError(user, "Error, you are not logged in");
        } else {
            Registry.AuthedUsers.remove(authedUser);
            IRCUtils.sendMessage(user, network, channel, "You are now logged out", prefix);
        }
    }
}

