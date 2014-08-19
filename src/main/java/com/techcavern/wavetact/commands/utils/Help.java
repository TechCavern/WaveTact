package com.techcavern.wavetact.commands.utils;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.Arrays;

public class Help extends GenericCommand {
    @CMD
    @GenCMD

    public Help() {
        super(GeneralUtils.toArray("help h halp"), 0, "help (command)", "gets help on a command");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("permissions")) {
                IRCUtils.SendMessage(user, channel, "0 = Everyone, 5 = Voiced, 7 = Half-opped, 10 = Opped & Protected, 15 = Ownered, 18 = Network Admin,  9001 = Controller ", isPrivate);
            } else {
                GenericCommand command = GetUtils.getCommand(args[0]);
                if (command != null) {
                    IRCUtils.SendMessage(user, channel, "aliases: " + StringUtils.join(Arrays.asList(command.getCommandID()), " "), isPrivate);
                    String syntax = command.getSyntax();
                    if (!syntax.isEmpty())
                        IRCUtils.SendMessage(user, channel, "syntax: " + syntax, isPrivate);
                    IRCUtils.SendMessage(user, channel, GetUtils.getCommand(args[0]).getDesc(), isPrivate);
                } else {
                    IRCUtils.sendError(user, "Command does not exist");
                }
            }
        } else {
            IRCUtils.SendMessage(user, channel, "help [command] - Generally a + before something means editing it, and a - means removing it. None means adding it. - Time is in [time](s/m/h/d/w) format\n", isPrivate);


        }
    }
}
