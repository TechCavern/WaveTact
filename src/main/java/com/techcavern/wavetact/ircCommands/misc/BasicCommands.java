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
        super(GeneralUtils.toArray("version ping pong releases license source"), 0, "ping", "some basic commands", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        command = command.toLowerCase();
        switch (command) {
            case "version":
                IRCUtils.sendMessage(user, network, channel, Registry.Version, prefix);
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
            case "license":
                IRCUtils.sendMessage(user, network, channel, "MIT License - https://goo.gl/KIiJeF", prefix);
                break;
            case "source":
                IRCUtils.sendMessage(user, network, channel, "http://goo.gl/YP7t4N", prefix);
                break;
            default:
                IRCUtils.sendMessage(user, network, channel, "pong", prefix);


        }
    }
}
