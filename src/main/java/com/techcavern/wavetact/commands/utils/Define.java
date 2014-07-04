package com.techcavern.wavetact.commands.utils;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.utils.objects.Command;
import com.techcavern.wavetact.utils.GeneralUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.List;


public class Define extends Command {
    @CMD
    public Define() {
        super(GeneralUtils.toArray("define whatis"), 0, "Define [word]");
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {
        List<String> waResults = GeneralUtils.getWAResult("define " + StringUtils.join(args, " "));
        if (waResults.size() < 1 || waResults.get(1) == null) {
            event.getChannel().send().message("Unable to get response, try again or stop inputting gibberish");
        } else {
            event.getChannel().send().message(waResults.get(1));

        }
    }

}
