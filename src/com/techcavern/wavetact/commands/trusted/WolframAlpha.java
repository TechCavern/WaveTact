package com.techcavern.wavetact.commands.trusted;

import com.techcavern.wavetact.objects.Command;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.wolfram.alpha.WAPlainText;
import com.wolfram.alpha.WAPod;
import com.wolfram.alpha.WAQueryResult;
import com.wolfram.alpha.WASubpod;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jztech101 on 6/23/14.
 */
public class WolframAlpha extends Command {
    public WolframAlpha() {
        super("wa", 5, "wa [input]");
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {
        List<String> waresults = GeneralUtils.getWAResult(StringUtils.join(args," "));
        for(String results: waresults){
           // event.respond(results);
            System.out.println(results);
        }
        }
}