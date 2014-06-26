package com.techcavern.wavetact.commands.Fun;

import com.techcavern.wavetact.objects.Command;
import com.techcavern.wavetact.utils.GeneralRegistry;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.hooks.events.MessageEvent;

public class Nom extends Command {

    public Nom() {
        super("nom", 5, "nom [something]");
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {
        event.getChannel().send().action("noms on" + StringUtils.join(args, " "));
        event.getChannel().send().message("mmhmm");
    }
}