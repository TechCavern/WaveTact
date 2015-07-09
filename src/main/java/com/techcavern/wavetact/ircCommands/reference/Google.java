package com.techcavern.wavetact.ircCommands.reference;

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
public class Google extends IRCCommand {

    public Google() {
        super(GeneralUtils.toArray("google gsearch"), 0, "google (result #) [string to google]", "Googles something", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        int ArrayIndex = 0;
        if (GeneralUtils.isInteger(args[0])) {
            ArrayIndex = Integer.parseInt(args[0]) - 1;
            args = ArrayUtils.remove(args, 0);
        }
        JsonArray results = GeneralUtils.getJsonObject("https://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=" + StringUtils.join(args, "%20")).getAsJsonObject("responseData").getAsJsonArray("results");
        if (results.size() > 0) {
            if (results.size() - 1 >= ArrayIndex) {
                String title = results.get(ArrayIndex).getAsJsonObject().get("titleNoFormatting").getAsString();
                String content = results.get(ArrayIndex).getAsJsonObject().get("content").getAsString().replaceAll("<.*?>", "").replaceAll("&.*?;", "");
                String url = results.get(ArrayIndex).getAsJsonObject().get("unescapedUrl").getAsString();
                IRCUtils.sendMessage(user, network, channel, "[" + title + "] " + content, prefix);
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

