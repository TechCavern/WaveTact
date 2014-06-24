package com.techcavern.wavetact.commands.Utils;

import com.techcavern.wavetact.objects.Command;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.wolfram.alpha.WAQueryResult;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Created by jztech101 on 6/23/14.
 */
public class WolframAlpha extends Command {
    public WolframAlpha() {
        super("wa", 0, "wa [input]");
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {
        WAQueryResult result = GeneralUtils.getWAResult(args[0]);
        event.respond(result.toString()
        );
    }
}