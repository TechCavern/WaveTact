package com.techcavern.wavetact.utils;

import com.techcavern.wavetact.objects.*;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

/**
 * Created by jztech101 on 7/4/14.
 */
public class GetUtils {

    public static User getUserByNick(Channel c, String n) {
        for (User u : c.getUsers()) {
            if (u.getNick().equalsIgnoreCase(n)) {
                return u;
            }
        }
        return null;
    }

    public static Channel getChannelbyName(PircBotX b, String n) {
        for (Channel u : b.getUserBot().getChannels()) {
            if (u.getName().equalsIgnoreCase(n)) {
                return u;
            }
        }
        return null;
    }

    public static PircBotX getBotByNetwork(String n) {
        for (PircBotX c : GeneralRegistry.WaveTact.getBots()) {
            if (c.getServerInfo().getNetwork().equals(n)) {
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

    public static String getCommandChar(PircBotX b) {
        for (CommandChar d : GeneralRegistry.CommandChars) {
            if (d.getBot() == b) {
                return d.getCommandChar();
            }
        }
        return null;
    }

    public static UTime getQuietTime(String u) {
        for (UTime x : GeneralRegistry.QuietTimes) {
            if (x.getHostmask().equals(u)) {
                return x;
            }
        }
        return null;
    }

    public static UTime getBanTime(String u) {
        for (UTime x : GeneralRegistry.BanTimes) {
            if (x.getHostmask().equals(u)) {
                return x;
            }
        }
        return null;
    }
}
