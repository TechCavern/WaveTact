package com.techcavern.wavetact.commands.utils;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.objects.Command;
import com.techcavern.wavetact.utils.GeneralUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.List;


public class Define extends Command {
    @CMD
    public Define() {
        super("Define", 0, "Define [word]");
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {
        List<String> waresults = GeneralUtils.getWAResult("define " + StringUtils.join(args, " "));
        if (waresults.size() < 1) {
            event.getChannel().send().message("Unable to get response, try again or stop inputting gibberish");
        } else {
            event.getChannel().send().message(waresults.get(1));

        }
    }

}
