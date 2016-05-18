package com.techcavern.wavetact.ircCommands.misc;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.events.WhoisEvent;

import java.util.HashSet;
import java.util.Set;

@IRCCMD
public class Whois extends IRCCommand {

    public Whois() {
        super(GeneralUtils.toArray("whois who idletime idt idle"), 1, "whois (user)", "Gets some info on user", false);
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
            Set<String> results = new HashSet<>();
            results.add("is "+nick + "!" + event.getLogin() + "@" + event.getHostname());

           results.add("signed on " + GeneralUtils.getDateFromSeconds(event.getSignOnTime()));
           results.add("has been idle for " + GeneralUtils.getTimeFromSeconds((int) event.getIdleSeconds()));
            if(event.getRegisteredAs() != null){
                results.add("is registered as " + event.getRegisteredAs());
            }
            if (event.getAwayMessage() != null)
                results.add(IRCUtils.noPing(nick) + " is currently away! (" + event.getAwayMessage() + ")");
            IRCUtils.sendMessage(user, network, channel,IRCUtils.noPing(nick) + " " + StringUtils.join(results, ", "), prefix);
        }
    }
}
