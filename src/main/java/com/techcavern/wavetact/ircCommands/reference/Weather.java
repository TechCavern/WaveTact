package com.techcavern.wavetact.ircCommands.reference;

import com.google.gson.JsonObject;
import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.Registry;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@IRCCMD
public class Weather extends IRCCommand {

    public Weather() {
        super(GeneralUtils.toArray("weather temperature temp humid humidity wind wunderground wunder"), 0, "weather [zipcode][city]", "Gets weather in an area from wunderground", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        if (Registry.wundergroundapikey == null) {
            ErrorUtils.sendError(user, "Wunderground API key is null - contact bot controller to fix");
            return;
        }
        JsonObject weather = GeneralUtils.getJsonObject("http://api.wunderground.com/api/" + Registry.wundergroundapikey + "/conditions/q/" + StringUtils.join(args, "%20") + ".json").getAsJsonObject("current_observation");
        if (weather != null) {
            String City = weather.get("display_location").getAsJsonObject().get("full").getAsString();
            String Weather = weather.get("weather").getAsString();
            String Temp = weather.get("temperature_string").getAsString();
            String Humidity = weather.get("relative_humidity").getAsString() + " humidity";
            String Wind = weather.get("wind_string").getAsString();
            IRCUtils.sendMessage(user, network, channel, City + ": " + Weather + " - " + Temp + " - " + Humidity + " - " + Wind, prefix);
        } else {
            ErrorUtils.sendError(user, "Requested location not found");
        }
    }

}
