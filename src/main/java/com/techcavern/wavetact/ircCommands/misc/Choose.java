package com.techcavern.wavetact.ircCommands.misc;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.Arrays;
import java.util.Random;

@IRCCMD
public class Choose extends IRCCommand {

    public Choose() {
        super(GeneralUtils.toArray("choose chose choice"), 0, "choose [things to choose from]", "Chooses between some things", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        Random generator = new Random();
        IRCUtils.sendMessage(user,network, channel,args[generator.nextInt(args.length)],prefix);
    }
}
