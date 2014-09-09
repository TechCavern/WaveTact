package com.techcavern.wavetact.commands.utils;

import com.google.gson.JsonObject;
import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@CMD
@GenCMD
public class Wikipedia extends GenericCommand {

    public Wikipedia() {
        super(GeneralUtils.toArray("wikipedia wiki"), 0, "wikipedia [string to search wikipedia]", "searchs wikipedia for something");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        Document doc = null;
        String url = "http://en.wikipedia.org/wiki/" + StringUtils.join(args, "%20");
        try {
            doc = Jsoup.connect(url).get();
        }catch (HttpStatusException e){
            IRCUtils.sendError(user, "Query returned no results");
            return;
        }
        Elements content = doc.select("#mw-content-text").select("p");
        String title = doc.title().toString().replace(" - Wikipedia, the free encyclopedia", "");
        String text = content.get(0).toString().replaceAll("<.*?>", "").replaceAll("&.*?;", "").replaceAll("\\[.*\\]", "");
        if(text.length() > 700){
            text = StringUtils.substring(text, 0, 700) + "...";
        }
        IRCUtils.sendMessage(user, channel, title + ": " + text, isPrivate);
        IRCUtils.sendMessage(user, channel, url, isPrivate);
    }
}
