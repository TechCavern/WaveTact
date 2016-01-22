package com.techcavern.wavetact.ircCommands.netadmin;


import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.Registry;
import org.apache.commons.lang3.StringEscapeUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@IRCCMD
public class FlushWhoisCache extends IRCCommand {

    public FlushWhoisCache() {
        super(GeneralUtils.toArray("flushwhoiscache flushwhois"), 20, "flushwhois", "Flushes Whois Cache", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        Registry.whoisEventCache.get(network).clear();
    }
}

