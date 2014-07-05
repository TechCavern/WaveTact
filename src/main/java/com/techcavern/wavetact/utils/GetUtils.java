package com.techcavern.wavetact.utils;

import com.techcavern.wavetact.utils.objects.*;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

/**
 * Created by jztech101 on 7/4/14.
 */
public class GetUtils {

    public static User getUserByNick(Channel channelObject, String nick) {
        for (User u : channelObject.getUsers()) {
            if (u.getNick().equalsIgnoreCase(nick)) {
                return u;
            }
        }
        return null;
    }


    public static Channel getChannelbyName(PircBotX botObject, String channelName) {
        for (Channel u : botObject.getUserBot().getChannels()) {
            if (u.getName().equalsIgnoreCase(channelName)) {
                return u;
            }
        }
        return null;
    }

    public static PircBotX getBotByNetwork(String network) {
        for (PircBotX c : GeneralRegistry.WaveTact.getBots()) {
            if (c.getServerInfo().getNetwork().equals(network)) {
                return c;
            }
        }
        return null;
    }

    public static Command getCommand(String Command) {
        for (Command g : GeneralRegistry.Commands) {
            if (g.getCommand().equalsIgnoreCase(Command)) {
                return g;
            }
        }
        return null;

    }

    public static String getCommandChar(PircBotX botObject) {
        for (CommandChar d : GeneralRegistry.CommandChars) {
            if (d.getBot() == botObject) {
                return d.getCommandChar();
            }
        }
        return null;
    }

}
