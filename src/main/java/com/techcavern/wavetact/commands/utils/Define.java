package com.techcavern.wavetact.commands.utils;

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


public class Define extends GenericCommand {
    @CMD
    public Define() {
        super(GeneralUtils.toArray("define whatis"), 0, "Define [word]");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate,int UserPermLevel, String... args) throws Exception {
        List<String> waResults = GeneralUtils.getWAResult("define " + StringUtils.join(args, " "));
        if (waResults.size() < 1 || waResults.get(1).isEmpty()) {
            user.send().notice("Unable to get response, try again or stop inputting gibberish");
        } else {
            IRCUtils.SendMessage(user, channel, waResults.get(1), isPrivate);

        }
    }

}
