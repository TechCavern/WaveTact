package com.techcavern.wavetact.commands.trusted;

import com.techcavern.wavetact.objects.Command;
import com.techcavern.wavetact.utils.GeneralUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.List;


public class WolframAlpha extends Command {
    public WolframAlpha() {
        super("wa", 5, "wa [input] (return array value #)");
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {
        List<String> waresults = GeneralUtils.getWAResult(StringUtils.join(args, " "));

        if (args.length > 0) {
            event.respond(waresults.get(Integer.parseInt(args[1])));
        } else {
            for (int i = 0; i < waresults.size(); i++) {
                event.respond(waresults.get(i));
            }
        }
    }
}