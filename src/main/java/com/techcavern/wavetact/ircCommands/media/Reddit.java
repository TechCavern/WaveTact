package com.techcavern.wavetact.ircCommands.media;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.jsoup.HttpStatusException;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.Random;

@IRCCMD
public class Reddit extends IRCCommand {

    public Reddit() {
        super(GeneralUtils.toArray("reddit showerthought re subreddit"), 1, "reddit [subreddit]", "gets a random message from a subreddit", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        if(command.equalsIgnoreCase("showerthought")){
            args = new String[1];
            args[0] = "showerthoughts";
        }
        try {
            JsonArray results = GeneralUtils.getJsonObject("http://api.reddit.com/r/" + args[0] + "/?limit=25").get("data").getAsJsonObject().get("children").getAsJsonArray();
            if (results.size() < 1) {
                IRCUtils.sendError(user, network, channel, "Search returned no results", prefix);
            } else {
                JsonObject result = results.get(new Random().nextInt(results.size() - 1)).getAsJsonObject().get("data").getAsJsonObject();
                IRCUtils.sendMessage(user, network, channel, result.get("title").getAsString() + " by " + IRCUtils.noPing(result.get("author").getAsString()) + " - " + GeneralUtils.shortenURL(result.get("url").getAsString()), prefix);
            }
        }catch(HttpStatusException e){
            if(e.getStatusCode() == 403){
                IRCUtils.sendError(user,network,channel, "Private Subreddit!", prefix);
            }
        }

    }
}

