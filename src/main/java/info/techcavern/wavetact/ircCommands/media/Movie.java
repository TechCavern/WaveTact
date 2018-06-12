package info.techcavern.wavetact.ircCommands.media;

import com.google.gson.JsonObject;
import info.techcavern.wavetact.annot.IRCCMD;
import info.techcavern.wavetact.objects.IRCCommand;
import info.techcavern.wavetact.utils.DatabaseUtils;
import info.techcavern.wavetact.utils.GeneralUtils;
import info.techcavern.wavetact.utils.IRCUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import static info.techcavern.wavetactdb.Tables.CONFIG;

@IRCCMD
public class Movie extends IRCCommand {

    public Movie() {
        super(GeneralUtils.toArray("movie mov imdb"), 1, "movie [query movies]", "Searches omdb", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        String omdbapikey;
        if (DatabaseUtils.getConfig("omdbapikey") != null)
            omdbapikey = DatabaseUtils.getConfig("omdbapikey").getValue(CONFIG.VALUE);
        else {
            IRCUtils.sendError(user, network, channel, "OMDb api key is null - contact bot controller to fix", prefix);
            return;
        }
        JsonObject results = GeneralUtils.getJsonObject("http://www.omdbapi.com/?t=" + StringUtils.join(args, "%20") + "&apikey=" + omdbapikey +"&y=&plot=short&r=json");
        if (results.get("Response").getAsString().equalsIgnoreCase("false")) {
            IRCUtils.sendError(user, network, channel, "Search returned no results", prefix);
        } else {
            String response = "[" + results.get("Title").getAsString() + "]";
            String runtime = results.get("Runtime").getAsString();
            if (!runtime.equalsIgnoreCase("N/A")) {
                response += " - " + runtime;
            }
            String year = results.get("Year").getAsString();
            if (!year.equalsIgnoreCase("N/A")) {
                response += " - " + year;
            }
            String genre = results.get("Genre").getAsString();
            if (!genre.equalsIgnoreCase("N/A")) {
                response += " - " + genre;
            }
            String plot = results.get("Plot").getAsString();
            if (!plot.equalsIgnoreCase("N/A")) {
                if (plot.length() > 700) {
                    plot = StringUtils.substring(plot, 0, 700) + "...";
                }
                response += " - " + plot;
            }
            IRCUtils.sendMessage(user, network, channel, response, prefix);
        }
    }
}

