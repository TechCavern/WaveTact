package com.techcavern.wavetact.ircCommands.fun;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.Registry;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@IRCCMD
public class Quote extends IRCCommand {

    public Quote() {
        super(GeneralUtils.toArray("quote quo randquote"), 1, "quote", "Gets a random quote", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        Document doc = Jsoup.connect("http://wwww.quotationspage.com/random.php3").userAgent(Registry.USER_AGENT).get();
        String c = doc.select(".quote").get(0).text();
        String d = doc.select(".author").get(0).text();
        if (d.contains("-")) {
            if (!d.contains("("))
                d = d.split("-")[0];
            else
                d = d.split("\\(")[0];
        }
        IRCUtils.sendMessage(user, network, channel, c + " -" + d, prefix);
    }
}
