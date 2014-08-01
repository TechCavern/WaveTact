package com.techcavern.wavetact.commands.utils;

import com.google.gson.JsonObject;
import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;


public class Google extends GenericCommand {
    @CMD
    @GenCMD

    public Google() {
        super(GeneralUtils.toArray("google goo g"), 0, "google [string to google]" ,"googles something");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        JsonObject results = GeneralUtils.getJsonObject("https://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=" + URLEncoder.encode(StringUtils.join(args, " "), "UTF-8")).getAsJsonObject("responseData").getAsJsonArray("results").get(0).getAsJsonObject();
        if (results != null || !results.isJsonNull()) {
            IRCUtils.SendMessage(user, channel, results.get("titleNoFormatting").getAsString() + " - " + results.get("unescapedUrl").getAsString(), isPrivate);
        } else {
            user.send().notice("Search returned no results");
        }
    }
}

