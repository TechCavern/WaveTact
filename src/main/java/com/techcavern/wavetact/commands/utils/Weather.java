package com.techcavern.wavetact.commands.utils;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.utils.objects.Command;
import com.techcavern.wavetact.utils.GeneralUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.List;


public class Weather extends Command {
    @CMD
    public Weather() {
        super(GeneralUtils.toArray("weather temperature temp w"), 0, "weather [zipcode]");
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {
        List<String> waResults = GeneralUtils.getWAResult("Weather " + StringUtils.join(args, " "));
        if (waResults.size() < 1) {
            event.getChannel().send().message("Unable to get response, try again or stop inputting gibberish");
        } else {
            event.getChannel().send().message(waResults.get(1));

        }
    }

}
