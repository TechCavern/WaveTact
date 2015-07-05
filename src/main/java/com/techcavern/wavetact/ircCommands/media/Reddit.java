package com.techcavern.wavetact.ircCommands.media;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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

import java.util.Random;

@IRCCMD
public class Reddit extends IRCCommand {

    public Reddit() {
        super(GeneralUtils.toArray("reddit subreddit"), 0, "reddit [subreddit]", "gets a random message from a subreddit", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        JsonArray results = GeneralUtils.getJsonObject("http://api.reddit.com/r/" + args[0] + "/?limit=100").get("data").getAsJsonObject().get("children").getAsJsonArray();
        JsonObject result = results.get(new Random().nextInt(results.size() - 1)).getAsJsonObject().get("data").getAsJsonObject();
        IRCUtils.sendMessage(user, network, channel, result.get("title").getAsString() + " by " + result.get("author").getAsString(), prefix);
        IRCUtils.sendMessage(user, network, channel, result.get("url").getAsString(), prefix);

    }
}

