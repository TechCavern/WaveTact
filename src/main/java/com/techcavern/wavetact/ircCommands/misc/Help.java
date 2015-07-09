package com.techcavern.wavetact.ircCommands.misc;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.Arrays;

@IRCCMD
public class Help extends IRCCommand {

    public Help() {
        super(GeneralUtils.toArray("help halp"), 0, "help (command)", "Gets help on a command", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("permissions")) {
                IRCUtils.sendMessage(user, network, channel, "-2 = Ignored Completely, -1 = Commands Ignored, 0 = Everyone, 1 = Registered, 5 = Voiced/Trusted, 7 = Channel Half-Operator, 10 = Operator, 13 = Protected Channel Operator, 15 = Senior Channel Operator, 18 = Channel Administrator, 20 = Network Administrator", prefix);
            } else {
                IRCCommand irCommand = IRCUtils.getGenericCommand(args[0]);
                if (irCommand != null) {
                    IRCUtils.sendMessage(user, network, channel, "Variations: " + StringUtils.join(Arrays.asList(irCommand.getCommandID()), ", "), prefix);
                    String syntax = irCommand.getSyntax();
                    if (!syntax.isEmpty())
                        IRCUtils.sendMessage(user, network, channel, "Syntax: " + syntax, prefix);
                    IRCUtils.sendMessage(user, network, channel, irCommand.getDesc(), prefix);
                } else {
                    ErrorUtils.sendError(user, "Command does not exist");
                }
            }
        } else {
            IRCUtils.sendMessage(user, network, channel, "help (command) - Run list for available commands, generally a + before something means editing it, and a - means removing it. None means adding it. Time is in [time](s/m/h/d/w) format. [] is a required argument. () is an optional argument.", prefix);
        }
    }
}
