package com.techcavern.wavetact.utils;

import com.techcavern.wavetact.utils.objects.GenericCommand;
import com.techcavern.wavetact.utils.objects.NetProperty;
import com.techcavern.wavetact.utils.objects.NetworkAdmin;
import com.techcavern.wavetact.utils.objects.UTime;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;


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
        for (NetProperty d : GeneralRegistry.CommandChars) {
            if (d.getBot() == botObject) {
                return d.getCommandChar();
            }
        }
        return null;
    }

    public static String getAuthType(PircBotX botObject) {
        for (NetProperty d : GeneralRegistry.AuthType) {
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

    public static NetworkAdmin getNetworkAdminByNick(String nick, String Network) {
        for (NetworkAdmin networkAdmin : GeneralRegistry.NetworkAdmins) {
            if (networkAdmin.getUser().equalsIgnoreCase(nick) && networkAdmin.getNetwork().equalsIgnoreCase(Network)) {
                return networkAdmin;
            }
        }
        return null;
    }

    public static String getControllerByNick(String Nick) {
        for (String c : GeneralRegistry.Controllers) {
            if (c.equalsIgnoreCase(Nick)) {
                return c;
            }
        }
        return null;
    }

    public static String getIRCDNSBLbyDomain(String Domain) {
        for (String d : GeneralRegistry.IRCBLs) {
            if (d.equalsIgnoreCase(Domain)) {
                return d;
            }
        }
        return null;
    }

    public static String getDNSBLbyDomain(String Domain) {
        for (String d : GeneralRegistry.DNSBLs) {
            if (d.equalsIgnoreCase(Domain)) {
                return d;
            }
        }
        return null;
    }

}
