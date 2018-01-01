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
public class Rather extends IRCCommand {

    public Rather() {
        super(GeneralUtils.toArray("wouldyourather rather either"), 1, "wouldyourather", "Would you rather?", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        Document doc = Jsoup.connect("http://either.io/").userAgent(Registry.USER_AGENT).get();
        String button = "Would you rather " + GeneralUtils.stripHTML(doc.select(".result-1").get(0).select(".option-text").toString().toLowerCase()) + " (" +GeneralUtils.stripHTML(doc.select(".result-1").get(0).select(".percentage").select("span").toString()) +"%)" + " OR " + GeneralUtils.stripHTML(doc.select(".result-2").get(0).select(".option-text").toString().toLowerCase()) + " (" +GeneralUtils.stripHTML(doc.select(".result-2").get(0).select(".percentage").select("span").toString()) +"%)?";

        IRCUtils.sendMessage(user, network, channel, button, prefix);
    }
}