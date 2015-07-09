package com.techcavern.wavetact.ircCommands.fun;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.*;
import org.apache.commons.lang3.RandomUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import static com.techcavern.wavetactdb.Tables.CONFIG;

/**
 * Created by tom on 7/9/15.
 */
@IRCCMD
public class LastFM extends IRCCommand {

    public LastFM() {
        super(GeneralUtils.toArray("lastfm nowplaying np"), 0, "lastfm [user]", "lastfm nowplaying", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        String last = GeneralUtils.buildMessage(0, args.length, args);
        if(last.contains(" ")){ ErrorUtils.sendError(user, "Invalid username"); return; }
        String apikey;
        if (DatabaseUtils.getConfig("lastfmapikey") != null) apikey = DatabaseUtils.getConfig("lastfmapikey").getValue(CONFIG.VALUE);
        else{
            ErrorUtils.sendError(user, "API key is not defined.");
            return;
        }

        JsonObject jsonObject = GeneralUtils.getJsonObject("http://ws.audioscrobbler.com/2.0/?format=json&method=user.getrecenttracks&api_key="+apikey+"&user="+last);
        //recenttracks, track, 0
        if(jsonObject.get("error") != null){
            ErrorUtils.sendError(user, "LastFM API returned an error: "+jsonObject.get("message").toString());
            return;
        }

        JsonArray tracks = jsonObject.get("recenttracks").getAsJsonObject().get("track").getAsJsonArray();
        JsonObject mostRecent = tracks.get(0).getAsJsonObject();

        String trackname = mostRecent.get("name").toString();
        String artist = mostRecent.get("artist").getAsJsonObject().get("#text").getAsString();

        IRCUtils.sendMessage(user, network, channel, last+"'s last track was: "+trackname+" by "+artist, prefix);
    }
}
