package com.techcavern.wavetact.ircCommands.fun;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.Registry;
import org.apache.commons.lang3.RandomUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@IRCCMD
public class MLIA extends IRCCommand {

    public MLIA() {
        super(GeneralUtils.toArray("mlia"), 1, "mlia", "Sends random mlia", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        int x = RandomUtils.nextInt(1,3300000);
        Document doc = Jsoup.connect("http://mylifeisaverage.com/story/" + x).userAgent(Registry.USER_AGENT).get();
        Elements MLI = doc.select(".sc");
        String mlia = GeneralUtils.stripHTML(MLI.toString()).trim();
        IRCUtils.sendMessage(user, network, channel, mlia, prefix);
    }
}