package com.techcavern.wavetact.ircCommands.media;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.DatabaseUtils;
import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import static com.techcavern.wavetactdb.Tables.CONFIG;

/**
 * Created by tom on 7/9/15.
 */
@IRCCMD
public class NowPlaying extends IRCCommand {

    public NowPlaying() {
        super(GeneralUtils.toArray("nowplaying np"), 0, "nowplaying [user]", "Gets last played song from Last.fm", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        String lastfmapikey;
        if (DatabaseUtils.getConfig("lastfmapikey") != null)
            lastfmapikey = DatabaseUtils.getConfig("lastfmapikey").getValue(CONFIG.VALUE);
        else {
            ErrorUtils.sendError(user, "Last.fm API key is not defined - Contact Bot Controller to fix.");
            return;
        }

        JsonObject jsonObject = GeneralUtils.getJsonObject("http://ws.audioscrobbler.com/2.0/?format=json&method=user.getrecenttracks&api_key=" + lastfmapikey + "&user=" + args[0]);
        if (jsonObject.get("error") != null) {
            ErrorUtils.sendError(user, "LastFM API returned an error: " + jsonObject.get("message").toString());
            return;
        }

        JsonArray tracks = jsonObject.get("recenttracks").getAsJsonObject().get("track").getAsJsonArray();

        for (int i = 0; i < 3; i++) {
            try {
                String trackname = tracks.get(i).getAsJsonObject().get("name").toString().replaceAll("\"", "");
                String artist = tracks.get(i).getAsJsonObject().get("artist").getAsJsonObject().get("#text").getAsString();
                String album = tracks.get(i).getAsJsonObject().get("album").getAsJsonObject().get("#text").getAsString();
                if (!album.isEmpty())
                IRCUtils.sendMessage(user, network, channel, "[" + album + "] " + trackname + " by " + artist, prefix);
                else
                    IRCUtils.sendMessage(user, network, channel, trackname + " by " + artist, prefix);

            } catch (ArrayIndexOutOfBoundsException e) {
                return;
            }
        }
    }
}
