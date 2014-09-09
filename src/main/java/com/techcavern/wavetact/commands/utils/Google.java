package com.techcavern.wavetact.commands.utils;

import com.google.gson.JsonArray;
import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@CMD
@GenCMD
public class Google extends GenericCommand {

    public Google() {
        super(GeneralUtils.toArray("google gsearch"), 0, "google (result #) [string to google]", "googles something");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        int ArrayIndex = 0;
        if (GeneralUtils.isInteger(args[0])) {
            ArrayIndex = Integer.parseInt(args[0]) - 1;
            args = ArrayUtils.remove(args, 0);
        }
        JsonArray results = GeneralUtils.getJsonObject("https://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=" + StringUtils.join(args, "%20")).getAsJsonObject("responseData").getAsJsonArray("results");
        if (results.size() - 1 > 0) {
            if (results.size() >= ArrayIndex) {
                String title = results.get(ArrayIndex).getAsJsonObject().get("titleNoFormatting").getAsString();
                String content = results.get(ArrayIndex).getAsJsonObject().get("content").getAsString().replaceAll("<.*?>", "").replaceAll("&.*?;", "");
                String url = results.get(ArrayIndex).getAsJsonObject().get("unescapedUrl").getAsString();
                IRCUtils.sendMessage(user, channel, title + " - " + content, isPrivate);
                IRCUtils.sendMessage(user, channel, url, isPrivate);

            } else {
                ArrayIndex = ArrayIndex + 1;
                IRCUtils.sendError(user, "Search #" + ArrayIndex + " does not exist");
            }
        } else {
            IRCUtils.sendError(user, "Search returned no results");
        }
    }
}

