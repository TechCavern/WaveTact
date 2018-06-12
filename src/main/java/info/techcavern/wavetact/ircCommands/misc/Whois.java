package info.techcavern.wavetact.ircCommands.misc;

import info.techcavern.wavetact.annot.IRCCMD;
import info.techcavern.wavetact.objects.IRCCommand;
import info.techcavern.wavetact.utils.GeneralUtils;
import info.techcavern.wavetact.utils.IRCUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.events.WhoisEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@IRCCMD
public class Whois extends IRCCommand {

    public Whois() {
        super(GeneralUtils.toArray("whois who idletime idt idle"), 1, "whois (-all) (user)", "Gets some info on user (-all shows channels)", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        String nick = user.getNick();
        boolean showChannels = false;
        if (args.length >= 2) {
            if(args[0].equalsIgnoreCase("-all") && userPermLevel >= 20)
            showChannels = true;
            nick = args[1];
        }else if(args.length == 1){
            if(args[0].equalsIgnoreCase("-all"))
                showChannels = true;
            else
            nick = args[0];
        }
        WhoisEvent event = IRCUtils.WhoisEvent(network, nick, false);
        if (event == null) {
            IRCUtils.sendError(user, network, channel, "User does not exist", prefix);
        } else {
            List<String> results = new ArrayList<>();
            results.add("is "+nick + "!" + event.getLogin() + "@" + event.getHostname());

           results.add("signed on " + GeneralUtils.getDateFromSeconds(event.getSignOnTime()));
           results.add("has been idle for " + GeneralUtils.getTimeFromSeconds((int) event.getIdleSeconds()));
            if(showChannels && event.getChannels() != null){
                results.add("is in " + StringUtils.join(event.getChannels(),", "));
            }
            if(event.getRegisteredAs() != null){
                results.add("is registered as " + event.getRegisteredAs());
            }
            if (event.getAwayMessage() != null)
                results.add(IRCUtils.noPing(nick) + " is currently away! (" + event.getAwayMessage() + ")");
            IRCUtils.sendMessage(user, network, channel,IRCUtils.noPing(nick) + " " + StringUtils.join(results, ", "), prefix);
        }
    }
}
