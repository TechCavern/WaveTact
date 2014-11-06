package com.techcavern.wavetact.commands.media;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@CMD
@GenCMD
public class Movie extends GenericCommand {

    public Movie() {
        super(GeneralUtils.toArray("movie imdb"), 0, "movie [string to search movies]", "searches imdb");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        JsonObject results = GeneralUtils.getJsonObject("http://www.omdbapi.com/?t=" + StringUtils.join(args, "%20") + "&y=&plot=full&r=json");
        if(results.get("Response").getAsString().equalsIgnoreCase("false")){
            IRCUtils.sendError(user, "Search returned no results");
        }else{
            String title = results.get("Title").getAsString();
            String response = title;
            String runtime = results.get("Runtime").getAsString();
            if(!runtime.equalsIgnoreCase("N/A")){
                response += " - " + runtime;
            }
            String year = results.get("Year").getAsString();
            if(!year.equalsIgnoreCase("N/A")){
                response += " - " + year;
            }
            String genre = results.get("Genre").getAsString();
            if(!genre.equalsIgnoreCase("N/A")){
                response += " - " + genre;
            }
            String plot = results.get("Plot").getAsString();
            if(!plot.equalsIgnoreCase("N/A")) {
                if(plot.length() > 700){
                    plot = StringUtils.substring(plot, 0, 700) + "...";
                }
                response += " - " + plot;
            }
            IRCUtils.sendMessage(user, channel, response, isPrivate);
        }
    }
}

