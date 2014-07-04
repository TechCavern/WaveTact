package com.techcavern.wavetact.commands.trusted;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.utils.objects.Command;
import com.techcavern.wavetact.utils.GeneralUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.List;


public class WolframAlpha extends Command {
    @CMD
    public WolframAlpha() {
        super(GeneralUtils.toArray("wolframalpha wa wolfram"), 5, "wolframalpha [input] (return array value #)");
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {
        List<String> waresults = GeneralUtils.getWAResult(StringUtils.join(args, " "));

        if (args.length > 1) {
            event.respond(waresults.get(Integer.parseInt(args[1])));
        } else {
            for (String waresult : waresults) {
                event.respond(waresult);
            }
        }
    }
}