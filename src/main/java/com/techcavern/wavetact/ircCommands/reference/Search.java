package com.techcavern.wavetact.ircCommands.reference;

import com.google.gson.JsonArray;
import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@IRCCMD
public class Search extends IRCCommand {

    public Search() {
        super(GeneralUtils.toArray("search google g goog gsearch"), 1, "search (result #) [string to google]", "Googles something", false);
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
                String content = GeneralUtils.stripHTML(results.get(ArrayIndex).getAsJsonObject().get("content").getAsString());
                String url = results.get(ArrayIndex).getAsJsonObject().get("unescapedUrl").getAsString();
                IRCUtils.sendMessage(user, network, channel, "[" + title + "] " + content + " - " + GeneralUtils.shortenURL(url), prefix);
            } else {
                ArrayIndex = ArrayIndex + 1;
                IRCUtils.sendError(user, network, channel, "Search #" + ArrayIndex + " does not exist", prefix);
            }
        } else {
            IRCUtils.sendError(user, network, channel, "Search returned no results", prefix);
        }
    }
}

