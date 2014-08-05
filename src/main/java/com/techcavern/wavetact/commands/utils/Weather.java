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

import java.util.List;


public class Weather extends GenericCommand {
    @CMD
    @GenCMD

    public Weather() {
        super(GeneralUtils.toArray("weather temperature temp w humid humidity wind"), 0, "weather [zipcode][city]", "gets weather in an area");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        if(GeneralRegistry.wundergroundapikey == null){
            user.send().notice("Wunderground API key is null - Contact Bot Controller to fix");
            return;
        }
        JsonObject weather = GeneralUtils.getJsonObject("http://api.wunderground.com/api/" + GeneralRegistry.wundergroundapikey +"/conditions/q/" + StringUtils.join(args, "%20") + ".json").getAsJsonObject("current_observation");
        if(weather != null) {
            String City = weather.get("display_location").getAsJsonObject().get("full").getAsString();
            String Weather = weather.get("weather").getAsString();
            String Temp = weather.get("temperature_string").getAsString();
            String Humidity = weather.get("relative_humidity").getAsString() + " humidity";
            String Wind = weather.get("wind_string").getAsString() + " " + weather.get("wind_gust_kph").getAsString() + " kph" + " " + weather.get("wind_dir").getAsString() + "winds";
            IRCUtils.SendMessage(user, channel, City + ": " + Weather + " - " + Temp + " - " + Humidity + " - " + Wind + " - ", isPrivate);
        }else{
            user.send().notice("Requested location not found");
        }
        }

}
