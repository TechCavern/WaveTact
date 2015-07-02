package com.techcavern.wavetact.ircCommands.fun;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@IRCCMD
public class Quote extends IRCCommand {

    public Quote() {
        super(GeneralUtils.toArray("quote randquote"), 0, "quote", "Gets a random quote", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        Document doc = Jsoup.connect("http://wwww.quotationspage.com/random.php3").get();
        String c = doc.select(".quote").text();
        String d = doc.select(".author").text();
        IRCUtils.sendMessage(user, network, channel, c +" -" + d, prefix);
    }
}
