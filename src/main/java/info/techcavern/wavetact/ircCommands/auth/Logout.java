package info.techcavern.wavetact.ircCommands.auth;

import info.techcavern.wavetact.annot.IRCCMD;
import info.techcavern.wavetact.objects.IRCCommand;
import info.techcavern.wavetact.utils.GeneralUtils;
import info.techcavern.wavetact.utils.IRCUtils;
import info.techcavern.wavetact.utils.Registry;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@IRCCMD
public class Logout extends IRCCommand {

    public Logout() {
        super(GeneralUtils.toArray("logout"), 0, "logout", "Logs you out", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        Registry.authedUsers.get(network).remove(IRCUtils.getHostmask(network, user.getNick(), false));
        Registry.whoisEventCache.get(network).remove(user.getNick());
        IRCUtils.sendMessage(user, network, channel, "You are now logged out", prefix);
        IRCUtils.sendLogChanMsg(network, "[LOGOUT] " + IRCUtils.noPing(user.getNick()));
    }
}
