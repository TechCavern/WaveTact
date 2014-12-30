package com.techcavern.wavetact.commands.utils;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.utils.ErrorUtils;
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
        super(GeneralUtils.toArray("help halp"), 0, "help (command)", "Gets help on a command - generally a + before something means editing it, and a - means removing it. None means adding it. Time is in [time](s/m/h/d/w) format", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("permissions")) {
                IRCUtils.sendMessage(user, network, channel, "0 = Everyone, 5 = Voiced/Trusted, 7 = Half-Opped, 10 = Opped & Protected, 15 = Ownered, 18 = Network Admin,  9001 = Controller ", prefix);
            } else {
                GenericCommand command = GetUtils.getGenericCommand(args[0]);
                if (command != null) {
                    IRCUtils.sendMessage(user, network, channel, "Aliases: " + StringUtils.join(Arrays.asList(command.getCommandID()), ", "), prefix);
                    String syntax = command.getSyntax();
                    if (!syntax.isEmpty())
                        IRCUtils.sendMessage(user, network, channel, "Syntax: " + syntax, prefix);
                    IRCUtils.sendMessage(user, network, channel, command.getDesc(), prefix);
                } else {
                    ErrorUtils.sendError(user, "Command does not exist");
                }
            }
        } else {
            IRCUtils.sendMessage(user, network, channel, "help [command] - generally a + before something means editing it, and a - means removing it. None means adding it. - Time is in [time](s/m/h/d/w) format", prefix);
        }
    }
}
