package com.techcavern.wavetact.ircCommands.auth;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.Registry;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@IRCCMD
public class Logout extends IRCCommand {

    public Logout() {
        super(GeneralUtils.toArray("logout"), 1, "logout", "Logs you out", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        Registry.authedUsers.get(network).remove(IRCUtils.getHostmask(network, user.getNick(), true));
        Registry.whoisEventCache.get(network).remove(user.getNick());
        IRCUtils.sendMessage(user, network, channel, "You are now logged out", prefix);
        IRCUtils.sendLogChanMsg(network, "[LOGOUT] " + IRCUtils.noPing(user.getNick()));
    }
}