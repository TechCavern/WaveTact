package com.techcavern.wavetact.ircCommands.reference;

import com.google.gson.JsonObject;
import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@IRCCMD
public class Wiki extends IRCCommand {

    public Wiki() {
        super(GeneralUtils.toArray("wiki wikipedia"), 0, "wiki [query wiki]", "Searches wikipedia for something", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        for (int i = 0; i < args.length; i++) {
            args[i] = StringUtils.capitalize(args[i]);
        }
        JsonObject result = GeneralUtils.getJsonObject("https://en.wikipedia.org/w/api.php?action=query&prop=extracts&explaintext&exsectionformat=plain&exchars=650&titles=" + StringUtils.join(args, "%20") + "&format=json").getAsJsonObject("query").getAsJsonObject("pages");
        String key = result.entrySet().iterator().next().getKey();
        if (result.getAsJsonObject(key).get("missing") != null) {
            ErrorUtils.sendError(user, "Query returned no results");
        } else {
            String title = result.getAsJsonObject(key).get("title").getAsString();
            String text = result.getAsJsonObject(key).get("extract").getAsString();
            String plainurl = "https://en.wikipedia.org/wiki/" + StringUtils.join(args, "%20");
            IRCUtils.sendMessage(user, network, channel, "[" + title + "] " + text.replaceAll("\n", " "), prefix);
            IRCUtils.sendMessage(user, network, channel, plainurl, prefix);
        }
    }
}
