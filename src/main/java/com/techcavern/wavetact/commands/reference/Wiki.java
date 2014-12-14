package com.techcavern.wavetact.commands.reference;

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

@CMD
@GenCMD
public class Wiki extends GenericCommand {

    public Wiki() {
        super(GeneralUtils.toArray("wiki wikipedia"), 0, "wiki [string to search wiki]", "searches wikipedia for something", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        JsonObject result = GeneralUtils.getJsonObject("http://en.wikipedia.org/w/api.php?action=query&prop=extracts&explaintext&exsectionformat=plain&exchars=700&titles=" + StringUtils.join(args, "%20").toLowerCase() + "&format=json").getAsJsonObject("query").getAsJsonObject("pages");
        String key = result.entrySet().iterator().next().getKey();
        if (result.getAsJsonObject(key).get("missing") != null) {
            IRCUtils.sendError(user, "Query returned no results");
        } else {
            String title = result.getAsJsonObject(key).get("title").getAsString();
            String text = result.getAsJsonObject(key).get("extract").getAsString();
            String plainurl = "http://en.wikipedia.org/" + StringUtils.join(args, "%20").toLowerCase();
            IRCUtils.sendMessage(user, network, channel, title + ": " + text.replaceAll("\n", ""), prefix);
            IRCUtils.sendMessage(user, network, channel, plainurl, prefix);
        }
    }
}
