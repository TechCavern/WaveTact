package com.techcavern.wavetact.commands.minecraft;

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
public class MCWiki extends GenericCommand {

    public MCWiki() {
        super(GeneralUtils.toArray("mcwiki mwiki"), 0, "mcwiki [string to search minecraft wikis]", "searches FTB-Wiki & FTBWiki for something");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        Document doc = null;
        String url = "http://wiki.feed-the-beast.com/" + StringUtils.join(args, "%20");
        try {
            doc = Jsoup.connect(url).get();
        }catch (HttpStatusException e){
            try{
                url = "http://ftbwiki.org/" + StringUtils.join(args, "%20");
                doc = Jsoup.connect(url).get();
            }catch (HttpStatusException ee) {
                IRCUtils.sendError(user, "Query returned no results");
                return;
            }
        }
        Elements content = doc.select("#mw-content-text").select("p");
        String title = doc.title().toString().replace(" - Feed The Beast wiki", "").replace(" - Feed The Beast Wiki", "");
        String text = "";
        int i = 0;
        while(text.replaceAll(" ","").isEmpty() && i <= content.size()){
           text = content.get(i).toString().replaceAll("<.*?>", "").replaceAll("&.*?;", "").replaceAll("\\[.*\\]", "");
           i++;
        }
        if(text.length() > 700){
            text = StringUtils.substring(text, 0, 700) + "...";
        }
        IRCUtils.sendMessage(user, channel, title + ": " + text, isPrivate);
        IRCUtils.sendMessage(user, channel, url, isPrivate);
    }
}
