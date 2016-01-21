package com.techcavern.wavetact.ircCommands.dnsinfo;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.Registry;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@IRCCMD
public class TLDInfo extends IRCCommand {

    public TLDInfo() {
        super(GeneralUtils.toArray("tldinfo tld"), 1, "tld [tld]", "Gets information on a TLDInfo from the iana db", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        if (!args[0].startsWith(".")) {
            args[0] = "." + args[0];
        }
        try {
            Document doc = Jsoup.connect("http://www.iana.org/domains/root/db/" + args[0]).userAgent(Registry.USER_AGENT).get();
            String c = doc.select("#main_right").text();
            IRCUtils.sendMessage(user, network, channel, c, prefix);
        }catch(HttpStatusException e){
            if(e.getStatusCode() == 404){
                IRCUtils.sendError(user, network, channel, "Invalid TLD", prefix);
            }else{
                IRCUtils.sendError(user, network, channel, "Failed to execute command, please make sure you are using the correct syntax (" + getSyntax() + ")", prefix);

            }
        }

    }
}
