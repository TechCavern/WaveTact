package com.techcavern.wavetact.commands.utils;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.apache.commons.lang3.StringUtils;
import org.pircbotz.Channel;
import org.pircbotz.PircBotZ;
import org.pircbotz.User;

import java.util.List;


public class Weather extends GenericCommand {
    @CMD
    @GenCMD

    public Weather() {
        super(GeneralUtils.toArray("weather temperature temp w"), 0, "weather [zipcode]");
    }

    @Override
    public void onCommand(User user, PircBotZ Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        List<String> waResults = GeneralUtils.getWAResult("Weather " + StringUtils.join(args, " "));
        if (waResults.size() < 1 || waResults.get(1).isEmpty()) {
            user.send().notice("Unable to get response, try again or stop inputting gibberish");
        } else {
            IRCUtils.SendMessage(user, channel, waResults.get(1), isPrivate);

        }
    }

}
