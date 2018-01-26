package com.techcavern.wavetact.ircCommands.misc;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.Registry;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@IRCCMD
public class BasicCommands extends IRCCommand {

    public BasicCommands() {
        super(GeneralUtils.toArray("version ping pong cookie permissions releases license source"), 0, "ping", "some basic commands", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        command = command.toLowerCase();
        switch (command) {
            case "version":
                IRCUtils.sendMessage(user, network, channel, Registry.VERSION, prefix);
                break;
            case "potato":
                IRCUtils.sendAction(user, network, channel, "is a potato", prefix);
                break;
            case "pong":
                IRCUtils.sendMessage(user, network, channel, "ping", prefix);
                break;
            case "releases":
                IRCUtils.sendMessage(user, network, channel, "https://goo.gl/4bNo6a", prefix);
                break;
            case "cookie"
                IRCUtils.sendAction(user, network, channel, "gives " + user.getNick() + " a cookie", prefix);
            case "license":
                IRCUtils.sendMessage(user, network, channel, "MIT License - https://goo.gl/KIiJeF", prefix);
                break;
            case "source":
                IRCUtils.sendMessage(user, network, channel, "http://goo.gl/YP7t4N", prefix);
                break;
            case "permissions":
                IRCUtils.sendMessage(user, network, channel, "-4 Banned, -3 = Ignored by Everything except Relay, -2 = Ignored by Everything except Relay & Auto-Voice, -1 = Commands Ignored, 0 = Everyone, 1 = Registered, 5 = Voiced/Trusted, 7 = Channel Half-Operator, 10 = Operator, 13 = Protected Channel Operator, 15 = Senior Channel Operator, 18 = Channel Administrator, 20 = Network Administrator", prefix);
                break;
            default:
                IRCUtils.sendMessage(user, network, channel, "pong", prefix);


        }
    }
}
