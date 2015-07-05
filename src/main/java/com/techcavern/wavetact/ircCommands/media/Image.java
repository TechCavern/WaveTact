package com.techcavern.wavetact.ircCommands.media;

import com.google.gson.JsonArray;
import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@IRCCMD
public class Image extends IRCCommand {

    public Image() {
        super(GeneralUtils.toArray("image images gimages googleimages"), 0, "image (result #) [string to search]", "Searches google images", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
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
                IRCUtils.sendMessage(user, network, channel, title + " - " + size, prefix);
                IRCUtils.sendMessage(user, network, channel, url, prefix);

            } else {
                ArrayIndex = ArrayIndex + 1;
                ErrorUtils.sendError(user, "Search #" + ArrayIndex + " does not exist");
            }
        } else {
            ErrorUtils.sendError(user, "Search returned no results");
        }
    }
}

