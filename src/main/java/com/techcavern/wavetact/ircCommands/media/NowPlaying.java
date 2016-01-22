package com.techcavern.wavetact.ircCommands.media;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.DatabaseUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.ArrayList;
import java.util.List;

import static com.techcavern.wavetactdb.Tables.CONFIG;

/**
 * Created by tom on 7/9/15.
 */
@IRCCMD
public class NowPlaying extends IRCCommand {

    public NowPlaying() {
        super(GeneralUtils.toArray("nowplaying np"), 1, "nowplaying [user]", "Gets last played song from Last.fm", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        String lastfmapikey;
        if (DatabaseUtils.getConfig("lastfmapikey") != null)
            lastfmapikey = DatabaseUtils.getConfig("lastfmapikey").getValue(CONFIG.VALUE);
        else {
            IRCUtils.sendError(user, network, channel, "Last.fm API key is not defined - Contact Bot Controller to fix.", prefix);
            return;
        }

        JsonObject jsonObject = GeneralUtils.getJsonObject("http://ws.audioscrobbler.com/2.0/?format=json&method=user.getrecenttracks&api_key=" + lastfmapikey + "&user=" + args[0]);
        if (jsonObject.get("error") != null) {
            IRCUtils.sendError(user, network, channel, "LastFM API returned an error: " + jsonObject.get("message").toString(), prefix);
            return;
        }

        JsonArray tracks = jsonObject.get("recenttracks").getAsJsonObject().get("track").getAsJsonArray();
        List<String> results = new ArrayList<>();
        if(tracks.size() < 1){
            IRCUtils.sendError(user, network, channel, "LastFM returned no results", prefix);
        }else{
        for (int i = 0; i < 3; i++) {
                String trackname = tracks.get(i).getAsJsonObject().get("name").toString().replaceAll("\"", "");
                String artist = tracks.get(i).getAsJsonObject().get("artist").getAsJsonObject().get("#text").getAsString();
                String album = tracks.get(i).getAsJsonObject().get("album").getAsJsonObject().get("#text").getAsString();
                if (!album.isEmpty())
                    results.add("[" + album + "] " + trackname + " by " + artist);
                else
                    results.add(trackname + " by " + artist);

        }}
        IRCUtils.sendMessage(user, network, channel, StringUtils.join(results, " - "),prefix);
    }
}
