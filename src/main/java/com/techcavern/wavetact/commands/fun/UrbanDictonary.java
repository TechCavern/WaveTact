package com.techcavern.wavetact.commands.fun;

import com.google.gson.JsonObject;
import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.objects.Command;
import com.techcavern.wavetact.utils.GeneralUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.hooks.events.MessageEvent;

public class UrbanDictonary extends Command {
    @CMD
    public UrbanDictonary() {
        super(GeneralUtils.toArray("urbandictionary ub urban urb"), 0, "urbandictionary [what to define]");
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {
        JsonObject g = GeneralUtils.getJsonObject("http://api.urbandictionary.com/v0/define?term=" + StringUtils.join(args).replaceAll(" ", "%20"));
        if (g.getAsJsonArray("list").size() > 0) {
            event.getChannel().send().message(g.getAsJsonArray("list").get(0).getAsJsonObject().get("definition").getAsString().replaceAll("\\n|\\r|\\t", " ").replaceAll("  ", " "));
        } else {
            event.getChannel().send().message("Not Defined in the Urban Dictionary");
        }

    }
}
