package com.techcavern.wavetact.commands.Utils;

import com.techcavern.wavetact.objects.Command;
import com.techcavern.wavetact.utils.GeneralUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.List;


public class Question extends Command {
    public Question() {
        super("Question", 0, "Question [Ask a Question]");
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {
        List<String> waresults = GeneralUtils.getWAResult("Question " + StringUtils.join(args, " "));
        if (waresults.size() < 1) {
            event.getChannel().send().message("Did you mean " + GeneralUtils.getWADidYouMeans("Question " + StringUtils.join(args, " ")));
        } else {
            event.getChannel().send().message(waresults.get(1));

        }
    }

}
