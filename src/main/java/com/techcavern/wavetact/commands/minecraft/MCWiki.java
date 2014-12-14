package com.techcavern.wavetact.commands.minecraft;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.apache.commons.lang3.StringUtils;
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
        super(GeneralUtils.toArray("mcwiki mwiki"), 0, "mcwiki [string to search minecraft wikis]", "searches Official Minecraft Wiki & FTB-Wiki & FTBWiki", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        Document doc = null;
        Elements content = null;
        String url = "http://minecraft.gamepedia.com/" + StringUtils.join(args, "%20");
        try {
            doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.17 (KHTML, like Gecko) Chrome/24.0.1312.57 Safari/537.17").get();
        } catch (Exception eee) {
            url = "http://wiki.feed-the-beast.com/" + StringUtils.join(args, "%20");
            try {
                doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.17 (KHTML, like Gecko) Chrome/24.0.1312.57 Safari/537.17").get();
            } catch (Exception e) {
                try {
                    url = "http://ftbwiki.org/" + StringUtils.join(args, "%20");
                    doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.17 (KHTML, like Gecko) Chrome/24.0.1312.57 Safari/537.17").get();
                } catch (Exception ee) {
                    IRCUtils.sendError(user, "Query returned no results Or Wikis are Down");
                    return;
                }
            }
        }
        content = doc.select("#mw-content-text");
        content.select(".notaninfobox").remove();
        content.select(".infobox").remove();
        content = content.select("p");
        String title = doc.title().toString().replace(" - Feed The Beast wiki", "").replace(" - Feed The Beast Wiki", "").replace(" - Minecraft Wiki", "");
        String text = "";
        int i = 0;
        while (text.replaceAll(" ", "").isEmpty() && i <= content.size()) {
            text = content.get(i).toString().replaceAll("<.*?>", "").replaceAll("&.*?;", "").replaceAll("\\[.*\\]", "");
            i++;
        }
        if (text.length() > 700) {
            text = StringUtils.substring(text, 0, 700) + "...";
        }
        IRCUtils.sendMessage(user, network, channel, title + ": " + text, prefix);
        IRCUtils.sendMessage(user, network, channel, url, prefix);
    }
}
