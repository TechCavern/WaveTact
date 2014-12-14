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

@CMD
@GenCMD
public class Help extends GenericCommand {

    public Help() {
        super(GeneralUtils.toArray("help halp"), 0, "help (command)", "gets help on a command", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("permissions")) {
                IRCUtils.sendMessage(user, network, channel, "0 = Everyone, 5 = Voiced, 7 = Half-opped, 10 = Opped & Protected, 15 = Ownered, 18 = Network Admin,  9001 = Controller ", prefix);
            } else {
                GenericCommand command = GetUtils.getCommand(args[0]);
                if (command != null) {
                    IRCUtils.sendMessage(user, network, channel, "aliases: " + StringUtils.join(Arrays.asList(command.getCommandID()), " "), prefix);
                    String syntax = command.getSyntax();
                    if (!syntax.isEmpty())
                        IRCUtils.sendMessage(user, network, channel, "syntax: " + syntax, prefix);
                    IRCUtils.sendMessage(user, network, channel, GetUtils.getCommand(args[0]).getDesc(), prefix);
                } else {
                    IRCUtils.sendError(user, "Command does not exist");
                }
            }
        } else {
            IRCUtils.sendMessage(user, network, channel, "help [command] - Generally a + before something means editing it, and a - means removing it. None means adding it. - Time is in [time](s/m/h/d/w) format\n", prefix);


        }
    }
}
