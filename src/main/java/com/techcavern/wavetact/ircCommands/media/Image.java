package com.techcavern.wavetact.ircCommands.media;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

//@IRCCMD
public class Image extends IRCCommand {

    public Image() {
        super(GeneralUtils.toArray("image images im gimages googleimages"), 1, "image (result #) [query]", "Searches google images", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        int ArrayIndex = 0;
        if (GeneralUtils.isInteger(args[0])) {
            ArrayIndex = Integer.parseInt(args[0]) - 1;
            args = ArrayUtils.remove(args, 0);
        }
        JsonObject moo = GeneralUtils.getJsonObject("https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + StringUtils.join(args, "%20"));
        JsonArray results = moo.getAsJsonObject("responseData").getAsJsonArray("results");
        if (results.size() - 1 > 0) {
            if (results.size() - 1 >= ArrayIndex) {
                String title = results.get(ArrayIndex).getAsJsonObject().get("titleNoFormatting").getAsString();
                String size = results.get(ArrayIndex).getAsJsonObject().get("width").getAsString() + "x" + results.get(ArrayIndex).getAsJsonObject().get("height").getAsString();
                String url = results.get(ArrayIndex).getAsJsonObject().get("unescapedUrl").getAsString();
                IRCUtils.sendMessage(user, network, channel, "[" + title + "] " + size + " - " + GeneralUtils.shortenURL(url), prefix);

            } else {
                ArrayIndex = ArrayIndex + 1;
                IRCUtils.sendError(user, network, channel, "Search #" + ArrayIndex + " does not exist", prefix);
            }
        } else {
            IRCUtils.sendError(user, network, channel, "Search returned no results", prefix);
        }
    }
}

