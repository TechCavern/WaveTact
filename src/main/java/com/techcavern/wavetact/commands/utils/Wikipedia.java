package com.techcavern.wavetact.commands.utils;

import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
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

import java.util.Iterator;

@CMD
@GenCMD
public class Wikipedia extends GenericCommand {

    public Wikipedia() {
        super(GeneralUtils.toArray("wikipedia wiki"), 0, "wikipedia [string to search wikipedia]", "searchs wikipedia for something");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        JsonObject result = GeneralUtils.getJsonObject("http://en.wikipedia.org/w/api.php?action=query&prop=extracts&explaintext&exsectionformat=plain&exchars=700&titles=" + StringUtils.join(args, "%20").toLowerCase() + "&format=json").getAsJsonObject("query").getAsJsonObject("pages");
        String key = result.entrySet().iterator().next().getKey();
        if(result.getAsJsonObject(key).get("missing") != null){
            IRCUtils.sendError(user, "Query returned no results");
        }else {
            String title = result.getAsJsonObject(key).get("title").getAsString();
            String text = result.getAsJsonObject(key).get("extract").getAsString();
            String plainurl = "http://en.wikipedia.org/" + StringUtils.join(args, "%20").toLowerCase();
            IRCUtils.sendMessage(user, channel, title + ": " + text.replaceAll("\n", ""), isPrivate);
            IRCUtils.sendMessage(user, channel, plainurl, isPrivate);
        }
    }
}
