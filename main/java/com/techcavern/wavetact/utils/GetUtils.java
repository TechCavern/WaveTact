package com.techcavern.wavetact.utils;

import com.techcavern.wavetact.utils.objects.CommandChar;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import com.techcavern.wavetact.utils.objects.Global;
import com.techcavern.wavetact.utils.objects.UTime;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

/**
 * Created by jztech101 on 7/4/14.
 */
public class GetUtils {

    public static User getUserByNick(PircBotX botObject, String Nick) {
        User userObject = botObject.getUserChannelDao().getUser(Nick);
        if (!userObject.getHostmask().isEmpty()) {
            return userObject;
        } else {
            return null;
        }
    }

    public static Channel getChannelbyName(PircBotX botObject, String channelName) {
        return botObject.getUserChannelDao().getChannel(channelName);

    }

    public static PircBotX getBotByNetwork(String network) {
        for (PircBotX c : GeneralRegistry.WaveTact.getBots()) {
            if (c.getServerInfo().getNetwork().equals(network)) {
                return c;
            }
        }
        return null;
    }

    public static GenericCommand getCommand(String Command) {
        for (GenericCommand g : GeneralRegistry.AllCommands) {
            for (String commandid : g.getCommandID()) {
                if (commandid.equalsIgnoreCase(Command)) {
                    return g;
                }
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

    public static UTime getTopic(String channelName, String networkName) {
        for (int i = GeneralRegistry.Topic.size() - 1; i > -1; i--) {
            UTime x = GeneralRegistry.Topic.get(i);
            if (x.getChannelName().equalsIgnoreCase(channelName) && x.getNetworkName().equalsIgnoreCase(networkName)) {
                return x;
            }
        }
        return null;
    }

    public static Global getGlobalByNick(String nick, String Network) {
        for (Global global : GeneralRegistry.Globals) {
            if (global.getUser().equals(nick) && global.getNetwork().equalsIgnoreCase(Network)) {
                return global;
            }
        }
        return null;
    }

    public static String getControllerByNick(String Nick) {
        for (String c : GeneralRegistry.Controllers) {
            if (c.equals(Nick)) {
                return c;
            }
        }
        return null;
    }

}
