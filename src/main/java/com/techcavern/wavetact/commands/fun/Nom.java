package com.techcavern.wavetact.commands.fun;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.objects.Command;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.hooks.events.MessageEvent;

public class Nom extends Command {

    @CMD
    public Nom() {
        super("nom", 0, "nom [something]");
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {
        if (args.length > 0) {
            event.getChannel().send().action("noms on " + StringUtils.join(args, " "));
        } else {
            event.getChannel().send().action("noms on " + event.getUser().getNick());
        }
        event.getChannel().send().message("mmhmm");
    }
}