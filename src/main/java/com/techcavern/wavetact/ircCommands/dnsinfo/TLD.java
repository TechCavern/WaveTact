package com.techcavern.wavetact.ircCommands.dnsinfo;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@IRCCMD
public class TLD extends IRCCommand {

    public TLD() {
        super(GeneralUtils.toArray("tld"), 1, "tld [tld]", "Checks if it is a valid TLD", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        String nick = user.getNick();
        boolean isBanmask = false;
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("+")) {
                isBanmask = true;
            } else if (args[0].startsWith("+")) {
                nick = args[0].replaceFirst("\\+", "");
                isBanmask = true;
            } else {
                nick = args[0];
            }
        }
        String hostmask = IRCUtils.getHostmask(network, nick, isBanmask);
        if (hostmask != null) {
            IRCUtils.sendMessage(user, network, channel, hostmask, prefix);
        } else {
            IRCUtils.sendError(user, network, channel, "User not found", prefix);
        }

    }
}
