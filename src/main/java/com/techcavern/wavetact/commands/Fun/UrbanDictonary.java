package com.techcavern.wavetact.commands.Fun;

import com.google.gson.JsonObject;
import com.techcavern.wavetact.objects.Command;
import com.techcavern.wavetact.utils.GeneralUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.hooks.events.MessageEvent;

public class UrbanDictonary extends Command {
    public UrbanDictonary() {
        super("urb", 0, "urb [what to define]");
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
