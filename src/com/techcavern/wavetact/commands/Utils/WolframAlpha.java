package com.techcavern.wavetact.commands.Utils;

import com.techcavern.wavetact.objects.Command;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.wolfram.alpha.WAPlainText;
import com.wolfram.alpha.WAPod;
import com.wolfram.alpha.WAQueryResult;
import com.wolfram.alpha.WASubpod;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.ArrayList;
import java.util.List;

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
        List<String> waresults= new ArrayList<String>();
        for (WAPod pod : result.getPods()) {
            if (!pod.isError()) {
                for (WASubpod subpod : pod.getSubpods()) {
                    for (Object element : subpod.getContents()) {
                        if (element instanceof WAPlainText) {
                            waresults.add(((WAPlainText) element).getText());
                        }
                    }
                }

            }
        }
        GeneralUtils.buildMessage(0,5, waresults.toArray(new String[waresults.size()]));
        }
}