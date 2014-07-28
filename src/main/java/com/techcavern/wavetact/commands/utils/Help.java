package com.techcavern.wavetact.commands.utils;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.apache.commons.lang3.StringUtils;
import org.pircbotz.Channel;
import org.pircbotz.PircBotZ;
import org.pircbotz.User;

import java.util.Arrays;

public class Help extends GenericCommand {
    @CMD
    @GenCMD

    public Help() {
        super(GeneralUtils.toArray("help h halp"), 0, "help (command)");
    }

    @Override
    public void onCommand(User user, PircBotZ Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("permissions")) {
                IRCUtils.SendMessage(user, channel, "0 = Everyone, 5 = Channel Trusted (Voice), 7 = Channel Half-op, 10 = Channel Op,13 = Channel Admin, 15 = Channel Owner, 18 = Channel Founder, 20 = Network Operator,  9001 = Controller ", isPrivate);
            } else {
                IRCUtils.SendMessage(user, channel, "aliases: " + StringUtils.join(Arrays.asList(GetUtils.getCommand(args[0]).getCommandID()), " "), isPrivate);
                IRCUtils.SendMessage(user, channel, GetUtils.getCommand(args[0]).getDesc(), isPrivate);
            }
        } else {
            IRCUtils.SendMessage(user, channel, "help [command] - Generally a + before something means editing it, and a - means removing it. None means adding it. - Time is in [time](s/m/h/d/w) format\n", isPrivate);


        }
    }
}
