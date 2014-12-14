package com.techcavern.wavetact.utils;

import com.techcavern.wavetact.utils.objects.GenericCommand;
import com.techcavern.wavetact.utils.objects.NetProperty;
import com.techcavern.wavetact.utils.objects.NetworkAdmin;
import com.techcavern.wavetact.utils.objects.UTime;
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

    public static PircBotX getBotByNetwork(String network) {
        for (PircBotX c : Constants.WaveTact.getBots()) {
            if (c.getServerInfo().getNetwork().equals(network)) {
                return c;
            }
        }
        return null;
    }

    public static GenericCommand getCommand(String Command) {
        for (GenericCommand g : Constants.AllCommands) {
            List<GenericCommand> b = Constants.AllCommands;
            for (String commandid : g.getCommandID()) {
                if (commandid.equalsIgnoreCase(Command)) {
                    return g;
                }
            }
        }
        return null;

    }

    public static String getCommandChar(PircBotX networkObject) {
        for (NetProperty d : Constants.CommandChars) {
            if (d.getBot() == networkObject) {
                return d.getProperty();
            }
        }
        return null;
    }

    public static String getNetAdminAccess(PircBotX networkObject) {
        for (NetProperty d : Constants.NetAdminAccess) {
            if (d.getBot() == networkObject) {
                return d.getProperty();
            }
        }
        return null;
    }

    public static PircBotX getBotByNetworkName(String name) {
        for (NetProperty d : Constants.NetworkName) {
            if (d.getProperty().equalsIgnoreCase(name)) {
                return d.getBot();
            }
        }
        return null;
    }

    public static String getNetworkNameByBot(PircBotX network) {
        for (NetProperty d : Constants.NetworkName) {
            if (d.getBot().equals(network)) {
                return d.getProperty();
            }
        }
        return null;
    }

    public static String getAuthType(PircBotX networkObject) {
        for (NetProperty d : Constants.AuthType) {
            if (d.getBot() == networkObject) {
                return d.getProperty();
            }
        }
        return null;
    }

    public static UTime getTopic(String channelName, String networkName) {
        for (int i = Constants.Topic.size() - 1; i > -1; i--) {
            UTime x = Constants.Topic.get(i);
            if (x.getChannelName().equalsIgnoreCase(channelName) && x.getNetworkName().equalsIgnoreCase(networkName)) {
                return x;
            }
        }
        return null;
    }

    public static NetworkAdmin getNetworkAdminByNick(String nick, String Network) {
        for (NetworkAdmin networkAdmin : Constants.NetworkAdmins) {
            if (networkAdmin.getUser().equalsIgnoreCase(nick) && networkAdmin.getNetwork().equalsIgnoreCase(Network)) {
                return networkAdmin;
            }
        }
        return null;
    }

    public static String getControllerByNick(String Nick) {
        for (String c : Constants.Controllers) {
            if (c.equalsIgnoreCase(Nick)) {
                return c;
            }
        }
        return null;
    }

    public static String getIRCDNSBLbyDomain(String Domain) {
        for (String d : Constants.IRCBLs) {
            if (d.equalsIgnoreCase(Domain)) {
                return d;
            }
        }
        return null;
    }

    public static String getDNSBLbyDomain(String Domain) {
        for (String d : Constants.DNSBLs) {
            if (d.equalsIgnoreCase(Domain)) {
                return d;
            }
        }
        return null;
    }

    public static List<String> getActionsList(int UserPermLevel) {
        return Constants.SimpleActions.stream().filter(g -> g.getPermLevel() <= UserPermLevel).map(GenericCommand::getCommand).collect(Collectors.toList());
    }

    public static List<String> getMessagesList(int UserPermLevel) {
        return Constants.SimpleMessages.stream().filter(g -> g.getPermLevel() <= UserPermLevel).map(GenericCommand::getCommand).collect(Collectors.toList());
    }

}
