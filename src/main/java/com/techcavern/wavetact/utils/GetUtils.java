package com.techcavern.wavetact.utils;

import com.techcavern.wavetact.objects.*;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.List;
import java.util.stream.Collectors;


public class GetUtils {

    public static User getUserByNick(PircBotX networkObject, String Nick) {
        try {
            User userObject = networkObject.getUserChannelDao().getUser(Nick);
            if (!userObject.getHostmask().isEmpty()) {
                return userObject;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static Channel getChannelbyName(PircBotX networkObject, String channelName) {
        return networkObject.getUserChannelDao().getChannel(channelName);

    }


    public static IRCCommand getGenericCommand(String Command) {
        for (IRCCommand g : Registry.AllCommands) {
            for (String commandid : g.getCommandID()) {
                if (commandid.equalsIgnoreCase(Command)) {
                    return g;
                }
            }
        }
        return null;

    }
    public static ConsoleCommand getConsoleCommand(String Command) {
        for (ConsoleCommand g : Registry.ConsoleCommands) {
            for (String commandid : g.getCommandID()) {
                if (commandid.equalsIgnoreCase(Command)) {
                    return g;
                }
            }
        }
        return null;

    }

    public static String getCommandChar(PircBotX networkObject) {
        for (NetProperty d : Registry.CommandChars) {
            if (d.getBot() == networkObject) {
                return d.getProperty();
            }
        }
        return null;
    }

    public static String getNetAdminAccess(PircBotX networkObject) {
        for (NetProperty d : Registry.NetAdminAccess) {
            if (d.getBot() == networkObject) {
                return d.getProperty();
            }
        }
        return null;
    }

    public static PircBotX getBotByNetworkName(String name) {
        for (NetProperty d : Registry.NetworkName) {
            if (d.getProperty().equalsIgnoreCase(name)) {
                return d.getBot();
            }
        }
        return null;
    }

    public static String getNetworkNameByNetwork(PircBotX network) {
        for (NetProperty d : Registry.NetworkName) {
            if (d.getBot().equals(network)) {
                return d.getProperty();
            }
        }
        return null;
    }

    public static String getAuthType(PircBotX networkObject) {
        for (NetProperty d : Registry.AuthType) {
            if (d.getBot() == networkObject) {
                return d.getProperty();
            }
        }
        return null;
    }

    public static ChannelProperty getTopic(String channelName, String networkName) {
        for (int i = Registry.Topic.size() - 1; i > -1; i--) {
            ChannelProperty x = Registry.Topic.get(i);
            if (x.getChannelName().equalsIgnoreCase(channelName) && x.getNetworkName().equalsIgnoreCase(networkName)) {
                return x;
            }
        }
        return null;
    }

    public static NetworkAdmin getNetworkAdminByNick(String nick, String Network) {
        for (NetworkAdmin networkAdmin : Registry.NetworkAdmins) {
            if (networkAdmin.getUser().equalsIgnoreCase(nick) && networkAdmin.getNetwork().equalsIgnoreCase(Network)) {
                return networkAdmin;
            }
        }
        return null;
    }

    public static String getControllerByNick(String Nick) {
        for (String c : Registry.Controllers) {
            if (c.equalsIgnoreCase(Nick)) {
                return c;
            }
        }
        return null;
    }

    public static String getIRCDNSBLbyDomain(String Domain) {
        for (String d : Registry.IRCBLs) {
            if (d.equalsIgnoreCase(Domain)) {
                return d;
            }
        }
        return null;
    }

    public static String getDNSBLbyDomain(String Domain) {
        for (String d : Registry.DNSBLs) {
            if (d.equalsIgnoreCase(Domain)) {
                return d;
            }
        }
        return null;
    }

    public static List<String> getActionsList(int UserPermLevel) {
        return Registry.SimpleActions.stream().filter(g -> g.getPermLevel() <= UserPermLevel).map(IRCCommand::getCommand).collect(Collectors.toList());
    }

    public static List<String> getMessagesList(int UserPermLevel) {
        return Registry.SimpleMessages.stream().filter(g -> g.getPermLevel() <= UserPermLevel).map(IRCCommand::getCommand).collect(Collectors.toList());
    }
    public static ChannelUserProperty getRelayBotbyBotName(PircBotX network, String channelName, String botName){
        String networkName = getNetworkNameByNetwork(network);
        for(ChannelUserProperty e:Registry.RelayBots){
            if(e.getChannelName().equals(channelName) && e.getNetworkName().equals(networkName) && e.getUser().equals(botName)){
                return e;
            }
        }
        return null;
    }

}
