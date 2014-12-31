package com.techcavern.wavetact.utils;

import com.techcavern.wavetact.objects.ConsoleCommand;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.objects.NetProperty;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Record;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.WaitForQueue;
import org.pircbotx.hooks.events.WhoisEvent;
import org.pircbotx.output.OutputChannel;

import static com.techcavern.wavetactdb.Tables.CUSTOMCOMMANDS;
import static com.techcavern.wavetactdb.Tables.SERVERS;


public class IRCUtils {
    public static void setMode(Channel channelObject, PircBotX networkObject, String modeToSet, String hostmask) {
        OutputChannel o = new OutputChannel(networkObject, channelObject);
        if (hostmask != null) {
            modeToSet = modeToSet + hostmask;
            o.setMode(modeToSet);
        } else {
            o.setMode(modeToSet);
        }
    }

    public static WhoisEvent WhoisEvent(PircBotX network, String userObject) {

        WhoisEvent WhoisEvent;
        network.sendIRC().whois(userObject);
        WaitForQueue waitForQueue = new WaitForQueue(network);
        try {
            WhoisEvent = waitForQueue.waitFor(WhoisEvent.class);
            waitForQueue.close();
        } catch (InterruptedException | NullPointerException ex) {
            ex.printStackTrace();
            WhoisEvent = null;
        }
        if (!WhoisEvent.isExists()) {
            return null;
        }
        return WhoisEvent;
    }

    public static void sendNotice(User userObject, PircBotX networkObject, Channel channelObject, String message, String prefix) {
        if (channelObject != null) {
            networkObject.sendRaw().rawLine("NOTICE " + prefix + channelObject.getName() + " :" + message);
        } else {
            userObject.send().notice(message);
        }
    }

    public static void sendMessage(User userObject, PircBotX networkObject, Channel channelObject, String message, String prefix) {
        if (channelObject != null) {
            for (int i = 0; i < message.length(); i += 350) {
                String messageToSend = message.substring(i, Math.min(message.length(), i + 350));
                if (!messageToSend.isEmpty())
                    networkObject.sendRaw().rawLine("PRIVMSG " + prefix + channelObject.getName() + " :" + messageToSend);
            }
        } else {
            userObject.send().message(message);
        }
    }

    public static void sendAction(User userObject, PircBotX networkObject, Channel channelObject, String message, String prefix) {
        if (channelObject != null) {
            networkObject.sendRaw().rawLine("PRIVMSG " + prefix + channelObject.getName() + " :\u0001ACTION " + message + "\u0001");
        } else {
            userObject.send().action(message);
        }
    }

    public static String getPrefix(PircBotX network, String fullChannelName) {
        String prefix = String.valueOf(fullChannelName.charAt(0));
        if (network.getServerInfo().getStatusMessage().contains(prefix)) {
            return prefix;
        }
        ;
        return "";
    }

    public static String getHostmask(PircBotX network, String userObject, boolean isBanmask) {
        String hostmask;
        WhoisEvent whois = WhoisEvent(network, userObject);
        if (whois != null) {
            String hostname = whois.getHostname();
            String Login = whois.getLogin();
            if (isBanmask) {
                if (!Login.startsWith("~")) {
                    hostmask = "*!" + Login + "@" + hostname;
                } else {
                    hostmask = "*!*@" + hostname;
                }
            } else {
                hostmask = "*!" + Login + "@" + hostname;
            }
            hostmask = hostmask.replace(" ", "");
        } else {
            hostmask = null;
        }
        return hostmask;
    }

    public static void sendGlobal(String message, User user) {
        for (PircBotX network : Registry.WaveTact.getBots()) {
            sendNetworkGlobal(message, network, user);
        }

    }

    public static void sendNetworkGlobal(String message, PircBotX network, User user) {
        for (Channel channel : network.getUserBot().getChannels()) {
            channel.send().notice("[" + user.getNick() + "]: " + message);
        }
    }

    public static String getIRCHostmask(PircBotX network, String userObject) {
        User whois = getUserByNick(network, userObject);
        String hostmask = "";
        if (whois != null) {
            String hostname = whois.getHostname();
            String Login = whois.getLogin();
            hostmask = "*!" + Login + "@" + hostname;
            hostmask = hostmask.replace(" ", "");
        } else {
            hostmask = null;
        }
        return hostmask;
    }

    public static String getHost(PircBotX network, String userObject) {
        String host;
        WhoisEvent whois = WhoisEvent(network, userObject);
        if (whois != null) {
            host = whois.getHostname();
        } else {
            host = null;
        }
        return host;
    }

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
        for (IRCCommand g : Registry.IRCCommands) {
            for (String commandid : g.getCommandID()) {
                if (commandid.equalsIgnoreCase(Command)) {
                    return g;
                }
            }
        }
        return null;

    }

    public static IRCCommand getCommand(String Command, String Network, String Channel) {
        IRCCommand cmd = getCustomCommand(Command, Network, Channel);
        if (cmd != null) {
            return cmd;
        } else {
            return getGenericCommand(Command);
        }
    }

    public static IRCCommand getCustomCommand(String Command, String Network, String Channel) {
        Record cmd = DatabaseUtils.getCustomCommand(Network, Channel, Command);
        if (cmd != null) {
            class SimpleCommand extends IRCCommand {
                public SimpleCommand() {
                    super(GeneralUtils.toArray(cmd.getValue(CUSTOMCOMMANDS.COMMAND)), cmd.getValue(CUSTOMCOMMANDS.PERMLEVEL), cmd.getValue(CUSTOMCOMMANDS.COMMAND), "Custom Command", false);

                }

                @Override
                public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
                    String action = cmd.getValue(CUSTOMCOMMANDS.VALUE);
                    String[] message = StringUtils.split(action, " ");
                    int i = 0;
                    for (String g : message) {
                        if (g.startsWith("$") && !g.contains("*")) {
                            action = action.replace(g, args[Integer.parseInt(g.replace("$", "")) - 1]);
                            if (Integer.parseInt(g.replace("$", "")) > i) {
                                i++;
                            }
                        }
                    }
                    action = action.replace("$*", GeneralUtils.buildMessage(i, args.length, args));
                    String responseprefix = DatabaseUtils.getConfig("commandchar");
                    if (action.startsWith(responseprefix)) {
                        action = action.replace(responseprefix, "");
                    }
                    if (cmd.getValue(CUSTOMCOMMANDS.ISACTION)) {
                        IRCUtils.sendAction(user, network, channel, action, prefix);
                    } else {
                        IRCUtils.sendMessage(user, network, channel, action, prefix);
                    }

                }
            }
            return new SimpleCommand();
        } else
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

    public static boolean isController(String account, String network) {
        for (String c : StringUtils.split(DatabaseUtils.getServer(network).getValue(SERVERS.CONTROLLERS), ", ")) {
            if (c.equalsIgnoreCase(account))
                return true;
        }
        return false;
    }

    public static boolean isNetworkAdmin(String account, String network) {
        for (String c : StringUtils.split(DatabaseUtils.getServer(network).getValue(SERVERS.NETWORKADMINS), ", ")) {
            if (c.equalsIgnoreCase(account))
                return true;
        }
        return false;
    }


}

