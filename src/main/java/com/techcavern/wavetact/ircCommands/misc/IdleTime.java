package com.techcavern.wavetact.ircCommands.misc;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.events.WhoisEvent;

@IRCCMD
public class IdleTime extends IRCCommand {

    public IdleTime() {
        super(GeneralUtils.toArray("idletime idt idle"), 1, "idle (user)", "Gets idle time", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        String nick = user.getNick();
        if (args.length > 0) {
            nick = args[0];
        }
        WhoisEvent event = IRCUtils.WhoisEvent(network, nick, false);
        if (event == null) {
            IRCUtils.sendError(user, network, channel, "User does not exist", prefix);
        } else {
            IRCUtils.sendMessage(user, network, channel, nick + " signed on " + GeneralUtils.getDateFromSeconds(event.getSignOnTime()), prefix);
            IRCUtils.sendMessage(user, network, channel, nick + " has been idle for " + GeneralUtils.getTimeFromSeconds((int) event.getIdleSeconds()), prefix);
            if (event.getAwayMessage() != null)
                IRCUtils.sendMessage(user, network, channel, nick + " is currently away! (" + event.getAwayMessage() + ")", prefix);
        }
    }
}
