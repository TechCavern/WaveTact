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
public class GImages extends GenericCommand {

    public GImages() {
        super(GeneralUtils.toArray("googleimages gimages gimage"), 0, "googleimages [string to google]", "googles something");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        int ArrayIndex = 0;
        if (GeneralUtils.isInteger(args[0])) {
            ArrayIndex = Integer.parseInt(args[0]) - 1;
            args = ArrayUtils.remove(args, 0);
        }
        JsonArray results = GeneralUtils.getJsonObject("https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + StringUtils.join(args, "%20")).getAsJsonObject("responseData").getAsJsonArray("results");
        if (results.size() - 1 > 0) {
            if (results.size() >= ArrayIndex) {
                String title = results.get(ArrayIndex).getAsJsonObject().get("titleNoFormatting").getAsString();
                String size = results.get(ArrayIndex).getAsJsonObject().get("width").getAsString() + "x" + results.get(ArrayIndex).getAsJsonObject().get("height").getAsString();
                String url = results.get(ArrayIndex).getAsJsonObject().get("unescapedUrl").getAsString();
                IRCUtils.SendMessage(user, channel, title + " - " + size, isPrivate);
                IRCUtils.SendMessage(user, channel, url, isPrivate);

            } else {
                ArrayIndex = ArrayIndex + 1;
                IRCUtils.sendError(user, "Search #" + ArrayIndex + " does not exist");
            }
        } else {
            IRCUtils.sendError(user, "Search returned no results");
        }
    }
}

