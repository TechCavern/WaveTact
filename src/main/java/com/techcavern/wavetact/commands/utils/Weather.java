package com.techcavern.wavetact.commands.utils;

import com.google.gson.JsonObject;
import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@CMD
@GenCMD
public class Weather extends GenericCommand {

    public Weather() {
        super(GeneralUtils.toArray("weather temperature temp humid humidity wind wunderground wunder"), 0, "weather [zipcode][city]", "gets weather in an area from wunderground", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        if (GeneralRegistry.wundergroundapikey == null) {
            IRCUtils.sendError(user, "Wunderground API key is null - Contact Bot Controller to fix");
            return;
        }
        JsonObject weather = GeneralUtils.getJsonObject("http://api.wunderground.com/api/" + GeneralRegistry.wundergroundapikey + "/conditions/q/" + StringUtils.join(args, "%20") + ".json").getAsJsonObject("current_observation");
        if (weather != null) {
            String City = weather.get("display_location").getAsJsonObject().get("full").getAsString();
            String Weather = weather.get("weather").getAsString();
            String Temp = weather.get("temperature_string").getAsString();
            String Humidity = weather.get("relative_humidity").getAsString() + " humidity";
            String Wind = weather.get("wind_string").getAsString();
            IRCUtils.sendMessage(user, network, channel, City + ": " + Weather + " - " + Temp + " - " + Humidity + " - " + Wind, prefix);
        } else {
            IRCUtils.sendError(user, "Requested location not found");
        }
    }

}
