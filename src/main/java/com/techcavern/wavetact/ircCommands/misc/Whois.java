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
public class Whois extends IRCCommand {

    public Whois() {
        super(GeneralUtils.toArray("whois who idletime idt idle"), 5, "whois (user)", "Gets some info on user", false);
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
            IRCUtils.sendMessage(user, network, channel, IRCUtils.noPing(nick) + " is "+nick + "!" + event.getLogin() + "@" + event.getHostname(), prefix);

            IRCUtils.sendMessage(user, network, channel, IRCUtils.noPing(nick) + " signed on " + GeneralUtils.getDateFromSeconds(event.getSignOnTime()), prefix);
            IRCUtils.sendMessage(user, network, channel, IRCUtils.noPing(nick) + " has been idle for " + GeneralUtils.getTimeFromSeconds((int) event.getIdleSeconds()), prefix);
            if(event.getRegisteredAs() != null){
                IRCUtils.sendMessage(user, network, channel, IRCUtils.noPing(nick) + " is registered as " + event.getRegisteredAs(), prefix);
            }
            if (event.getAwayMessage() != null)
                IRCUtils.sendMessage(user, network, channel, IRCUtils.noPing(nick) + " is currently away! (" + event.getAwayMessage() + ")", prefix);
        }
    }
}
