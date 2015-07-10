package com.techcavern.wavetact.ircCommands.media;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.DatabaseUtils;
import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import javassist.bytecode.stackmap.BasicBlock;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import static com.techcavern.wavetactdb.Tables.CONFIG;

@IRCCMD
public class Music extends IRCCommand {

    public Music() {
        super(GeneralUtils.toArray("music song artist album"), 0, "music (result #) [query]", "Searches for music - result # parameter only useful for artist/album querying ", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        String lastfmapikey;
        if (DatabaseUtils.getConfig("lastfmapikey") != null)
            lastfmapikey = DatabaseUtils.getConfig("lastfmapikey").getValue(CONFIG.VALUE);
        else {
            ErrorUtils.sendError(user, "API key is not defined.");
            return;
        }
        int ArrayIndex = 0;
        if (GeneralUtils.isInteger(args[0])) {
            ArrayIndex = Integer.parseInt(args[0]) - 1;
            args = ArrayUtils.remove(args, 0);
        }
        if (command.equalsIgnoreCase("album")) {
            JsonArray albumlist = GeneralUtils.getJsonObject("http://ws.audioscrobbler.com/2.0/?method=album.search&album=" + StringUtils.join(args, "%20") + "&api_key=" + lastfmapikey + "&format=json").get("results").getAsJsonObject().get("albummatches").getAsJsonObject().get("album").getAsJsonArray();
            if (albumlist.size() - 1 >= ArrayIndex) {
                JsonObject album = albumlist.get(ArrayIndex).getAsJsonObject();
                try {
                    JsonArray albumtracks = GeneralUtils.getJsonObject("http://ws.audioscrobbler.com/2.0/?method=album.getinfo&api_key=" + lastfmapikey + "&artist=" + album.get("artist").getAsString().replaceAll(" ", "%20") + "&album=" + album.get("name").getAsString().replaceAll(" ", "%20") + "&format=json").get("album").getAsJsonObject().get("tracks").getAsJsonObject().get("track").getAsJsonArray();
                    for (int i = 0; i < 3; i++) {
                        try {
                            IRCUtils.sendMessage(user, network, channel, "[" + album.get("name").getAsString() + "] " + albumtracks.get(i).getAsJsonObject().get("name").getAsString() + " by " + album.get("artist").getAsString(), prefix);
                        } catch (ArrayIndexOutOfBoundsException e) {
                            return;
                        }
                    }
                } catch (IllegalStateException e) {
                    JsonObject song = GeneralUtils.getJsonObject("http://ws.audioscrobbler.com/2.0/?method=album.getinfo&api_key=" + lastfmapikey + "&artist=" + album.get("artist").getAsString().replaceAll(" ", "%20") + "&album=" + album.get("name").getAsString().replaceAll(" ", "%20") + "&format=json").get("album").getAsJsonObject().get("tracks").getAsJsonObject().get("track").getAsJsonObject();
                    IRCUtils.sendMessage(user, network, channel, "[" + album.get("name").getAsString() + "] " + song.get("name").getAsString() + " by " + album.get("artist").getAsString(), prefix);

                }
            } else {
                ArrayIndex = ArrayIndex + 1;
                ErrorUtils.sendError(user, "result " + ArrayIndex + " does not exist");
                return;
            }
        } else if (command.equalsIgnoreCase("artist")) {
            JsonArray artistlist = GeneralUtils.getJsonObject("http://ws.audioscrobbler.com/2.0/?method=artist.search&artist=" + StringUtils.join(args, "%20") + "&api_key=" + lastfmapikey + "&format=json").get("results").getAsJsonObject().get("artistmatches").getAsJsonObject().get("artist").getAsJsonArray();
            if (artistlist.size() - 1 >= ArrayIndex) {
                JsonObject artist = artistlist.get(ArrayIndex).getAsJsonObject();
                JsonArray toptracks = GeneralUtils.getJsonObject("http://ws.audioscrobbler.com/2.0/?method=artist.gettoptracks&artist=" + artist.get("name").getAsString().replaceAll(" ", "%20") + "&api_key=" + lastfmapikey + "&format=json").get("toptracks").getAsJsonObject().get("track").getAsJsonArray();
                for (int i = 0; i < 3; i++) {
                    try {
                        IRCUtils.sendMessage(user, network, channel, toptracks.get(i).getAsJsonObject().get("name").getAsString() + " by " + artist.get("name").getAsString(), prefix);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        return;
                    }
                }
            } else {
                ArrayIndex = ArrayIndex + 1;
                ErrorUtils.sendError(user, "result " + ArrayIndex + " does not exist");
                return;
            }
        } else {
            JsonArray tracklist = GeneralUtils.getJsonObject("http://ws.audioscrobbler.com/2.0/?method=track.search&track=" + StringUtils.join(args, "%20") + "&api_key=" + lastfmapikey + "&format=json").get("results").getAsJsonObject().get("trackmatches").getAsJsonObject().get("track").getAsJsonArray();
            for (int i = 0; i < 3; i++) {
                try {
                    IRCUtils.sendMessage(user, network, channel, tracklist.get(i).getAsJsonObject().get("name").getAsString() + " by " + tracklist.get(i).getAsJsonObject().get("artist").getAsString(), prefix);
                } catch (ArrayIndexOutOfBoundsException e) {
                    return;
                }
            }
        }

    }
}
