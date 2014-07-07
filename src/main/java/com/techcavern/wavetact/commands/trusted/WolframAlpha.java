package com.techcavern.wavetact.commands.trusted;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.List;


public class WolframAlpha extends GenericCommand {
    @CMD
    public WolframAlpha() {
        super(GeneralUtils.toArray("wolframalpha wa wolfram"), 5, "wolframalpha [input] (return array value #)");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, String... args) throws Exception {
        List<String> waResults = GeneralUtils.getWAResult(StringUtils.join(args, " "));

        if (args.length > 1) {
            IRCUtils.SendMessage(user, channel, waResults.get(Integer.parseInt(args[1])), isPrivate);
        } else {
            for (String waresult : waResults) {
                IRCUtils.SendMessage(user, channel, waresult, isPrivate);
            }
        }
    }
}