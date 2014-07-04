package com.techcavern.wavetact.utils;

import com.techcavern.wavetact.utils.objects.*;
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

    public static SimpleMessage getSimpleMessage(String SimpleAction) {
        for (SimpleMessage g : GeneralRegistry.SimpleMessages) {
            if (g.getCommand().equalsIgnoreCase(SimpleAction)) {
                return g;
            }
        }
        return null;

    }

    public static SimpleAction getSimpleAction(String SimpleAction) {
        for (SimpleAction g : GeneralRegistry.SimpleActions) {
            if (g.getCommand().equalsIgnoreCase(SimpleAction)) {
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

    public static UTime getQuietTime(String hostmask) {
        for (UTime x : GeneralRegistry.QuietTimes) {
            if (x.getHostmask().equals(hostmask)) {
                return x;
            }
        }
        return null;
    }

    public static UTime getBanTime(String hostmask) {
        for (UTime x : GeneralRegistry.BanTimes) {
            if (x.getHostmask().equals(hostmask)) {
                return x;
            }
        }
        return null;
    }
}
