package com.techcavern.wavetact.utils;

import com.techcavern.wavetact.objects.CachedWhoisEvent;
import com.techcavern.wavetact.objects.ConsoleCommand;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.objects.NetMessage;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Record;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.UserLevel;
import org.pircbotx.exception.DaoException;
import org.pircbotx.hooks.WaitForQueue;
import org.pircbotx.hooks.events.WhoisEvent;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import static com.techcavern.wavetactdb.Tables.CUSTOMCOMMANDS;
import static com.techcavern.wavetactdb.Tables.NETWORKPROPERTY;


public class IRCUtils {
    public static void setMode(Channel channelObject, PircBotX networkObject, String modeToSet, String hostmask) {
        if (hostmask != null) {
            Registry.MessageQueue.add(new NetMessage("MODE " + channelObject.getName() + " " + modeToSet + " " + hostmask, networkObject));
        } else {
            Registry.MessageQueue.add(new NetMessage("MODE " + channelObject.getName() + " " + modeToSet, networkObject));
        }
    }

    public static WhoisEvent getCachedWhoisEvent(PircBotX network, String userObject) {
        for (CachedWhoisEvent event : Registry.WhoisEventCache) {
            if (event.getNetwork().equals(network) && event.getUser().equals(userObject)) {
                return event.getWhoisEvent();
            }
        }
        return null;
    }

    public static WhoisEvent WhoisEvent(PircBotX network, String userObject, boolean useCache) {
        WhoisEvent WhoisEvent = getCachedWhoisEvent(network, userObject);
        if (useCache) {
            if (WhoisEvent != null) {
                return WhoisEvent;
            } else if (Registry.LastWhois.equals(userObject)) {
                int i = 0;
                while (getCachedWhoisEvent(network, userObject) == null && i < 20) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (Exception e) {
                    }
                    i++;
                }
                if (getCachedWhoisEvent(network, userObject) != null)
                    return getCachedWhoisEvent(network, userObject);
            }
            Registry.LastWhois = userObject;
        } else if (WhoisEvent != null) {
            Registry.WhoisEventCache.remove(WhoisEvent);
        }
        WaitForQueue waitForQueue = new WaitForQueue(network);
        try {
            Registry.MessageQueue.add(new NetMessage("WHOIS " + userObject + " " + userObject, network));
            WhoisEvent = waitForQueue.waitFor(WhoisEvent.class);
            waitForQueue.close();
        } catch (InterruptedException | NullPointerException ex) {
            ex.printStackTrace();
            WhoisEvent = null;
        }
        if (WhoisEvent == null || !WhoisEvent.isExists() || !WhoisEvent.getNick().equals(userObject))
            return null;
        else
            Registry.WhoisEventCache.add(new CachedWhoisEvent(WhoisEvent, network, userObject));
        return WhoisEvent;
    }

    public static void sendMessage(User userObject, PircBotX networkObject, Channel channelObject, String message, String prefix) {
        if (channelObject != null) {
            for (int i = 0; i < message.length(); i += 350) {
                String messageToSend = message.substring(i, Math.min(message.length(), i + 350));
                if (!messageToSend.isEmpty()) {
                    Registry.MessageQueue.add(new NetMessage("PRIVMSG " + prefix + channelObject.getName() + " :" + messageToSend, networkObject));
                    if (prefix.isEmpty())
                        sendRelayMessage(networkObject, channelObject, GeneralUtils.replaceVowelsWithAccents(networkObject.getNick()) + ": " + message);
                }
            }
        } else {
            Registry.MessageQueue.add(new NetMessage("PRIVMSG " + userObject.getNick() + " :" + message, networkObject));
        }
    }

    public static void sendKick(User userObject, User recipientObject, PircBotX networkObject, Channel channelObject, String message) {
        if (channelObject != null) {
            for (int i = 0; i < message.length(); i += 350) {
                String messageToSend = message.substring(i, Math.min(message.length(), i + 350));
                if (!messageToSend.isEmpty()) {
                    Registry.MessageQueue.add(new NetMessage("KICK " + channelObject.getName() + " " + recipientObject.getNick() + " :" + messageToSend, networkObject));
                    //       sendRelayMessage(networkObject, channelObject, "* " + userObject.getNick() + " kicks " + recipientObject.getNick() + " (" + messageToSend + ")");
                }
            }
        }
    }

    public static void sendRelayMessage(PircBotX networkObject, Channel channel, String msg) {
        Record prerec = DatabaseUtils.getNetworkProperty(getNetworkNameByNetwork(networkObject), "relaychan");
        if (prerec != null) {
            String chnname = prerec.getValue(NETWORKPROPERTY.VALUE);
            if (chnname != null && chnname.equalsIgnoreCase(channel.getName())) {
                Iterator iterator = Registry.NetworkName.keySet().iterator();
                while (iterator.hasNext()) {
                    PircBotX net = (PircBotX) iterator.next();
                    if (net != networkObject) {
                        Record rec = DatabaseUtils.getNetworkProperty(Registry.NetworkName.get(net), "relaychan");
                        if (rec != null) {
                            String relaychan = rec.getValue(NETWORKPROPERTY.VALUE);
                            if (relaychan != null)
                                Registry.MessageQueue.add(new NetMessage("PRIVMSG " + relaychan + " :[" + getNetworkNameByNetwork(networkObject) + "] " + msg, net));
                        }
                    }
                }
            }
        }
    }

    public static void sendAction(User userObject, PircBotX networkObject, Channel channelObject, String message, String prefix) {
        if (channelObject != null) {
            Registry.MessageQueue.add(new NetMessage("PRIVMSG " + prefix + channelObject.getName() + " :\u0001ACTION " + message + "\u0001", networkObject));
            if (prefix.isEmpty())
                sendRelayMessage(networkObject, channelObject, "* " + GeneralUtils.replaceVowelsWithAccents(networkObject.getNick()) + " " + message);
        } else {
            Registry.MessageQueue.add(new NetMessage("PRIVMSG " + userObject.getNick() + " :\u0001ACTION " + message + "\u0001", networkObject));
        }
    }

    public static void sendAction(PircBotX networkObject, Channel channelObject, String message, String prefix) {
        sendAction(null, networkObject, channelObject, message, prefix);
    }

    public static void sendMessage(PircBotX networkObject, Channel channelObject, String message, String prefix) {
        sendMessage(null, networkObject, channelObject, message, prefix);

    }


    public static String getPrefix(PircBotX network, String fullChannelName) {
        String prefix = String.valueOf(fullChannelName.charAt(0));
        if (network.getServerInfo().getStatusMessage().contains(prefix)) {
            return prefix;
        }
        return "";
    }

    public static boolean checkIfCanKick(Channel channel, PircBotX network, User user) {
        Record relaybotsplit = DatabaseUtils.getChannelUserProperty(IRCUtils.getNetworkNameByNetwork(network), channel.getName(), PermUtils.authUser(network, user.getNick()), "relaybotsplit");
        if (relaybotsplit == null) {
            if (channel != null && channel.getUserLevels(network.getUserBot()).contains(UserLevel.OP) && !channel.isOwner(user) && !channel.isSuperOp(user)) {
                return true;
            } else if (channel != null && channel.getUserLevels(network.getUserBot()).contains(UserLevel.SUPEROP) && !channel.isOwner(user) && !channel.isSuperOp(user)) {
                return true;
            } else return channel != null && channel.getUserLevels(network.getUserBot()).contains(UserLevel.OWNER);
        } else {
            return false;
        }
    }

    public static String getHostmask(PircBotX network, String userObject, boolean isBanmask) {
        String hostmask = getIRCHostmask(network, userObject, isBanmask);
        if (hostmask == null) {
            hostmask = getWhoisHostmask(network, userObject, isBanmask);
        }
        return hostmask;
    }

    public static String getWhoisHostmask(PircBotX network, String userObject, boolean isBanmask) {
        String hostmask;
        WhoisEvent whois = WhoisEvent(network, userObject, true);
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
        Iterator iterator = Registry.NetworkName.keySet().iterator();
        while (iterator.hasNext())
            sendNetworkGlobal(message, (PircBotX) iterator.next(), user, true);
    }

    public static void sendNetworkGlobal(String message, PircBotX network, User user, boolean isGlobal) {
        String beginning = "[Network Notice] ";
        if (isGlobal) {
            beginning = "[Global Notice] ";
        }
        for (Channel channel : network.getUserBot().getChannels()) {
            if (user != null)
                channel.send().message(beginning + user.getNick() + " - " + message);
            else
                channel.send().message(beginning + message);
        }
    }

    public static String getIRCHostmask(PircBotX network, String userObject, boolean isBanmask) {
        User user = getUserByNick(network, userObject);
        String hostmask;
        if (user != null && user.getHostname() != null && user.getLogin() != null) {
            String hostname = user.getHostname();
            String Login = user.getLogin();
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

    public static String getHost(PircBotX network, String userObject) {
        String host = "";
        User use = getUserByNick(network, userObject);
        if (use != null && use.getHostname() != null) {
            host = use.getHostname();
        } else {
            WhoisEvent whois = WhoisEvent(network, userObject, true);
            if (whois != null) {
                host = whois.getHostname();
            } else {
                host = "";
            }
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
        try {
            return networkObject.getUserChannelDao().getChannel(channelName);
        } catch (DaoException e) {
            return null;
        }

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
                public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
                    String action = cmd.getValue(CUSTOMCOMMANDS.VALUE);
                    String[] message = StringUtils.split(action, " ");
                    int i = 0;
                    for (String g : message) {
                        if (g.startsWith("$") && !g.contains("*")) {
                            action = action.replace(g, args[Integer.parseInt(g.replace("$", "")) - 1]);
                            try {
                                if (Integer.parseInt(g.replace("$", "")) > i) {
                                    i++;
                                }
                            } catch (Exception e) {
                            }
                        }
                    }
                    action = action.replace("$*", GeneralUtils.buildMessage(i, args.length, args));
                    String responseprefix = DatabaseUtils.getNetworkProperty(IRCUtils.getNetworkNameByNetwork(network), "commandchar").getValue(NETWORKPROPERTY.VALUE);
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
        return Registry.NetworkBot.get(name);
    }

    public static String getNetworkNameByNetwork(PircBotX network) {
        return Registry.NetworkName.get(network);
    }

    public static Channel getMsgChannel(Channel channel, boolean isPrivate) {
        if (isPrivate) {
            return null;
        } else {
            return channel;
        }
    }


    public static void sendLogChanMsg(PircBotX network, String message) {
        Record pmlog = DatabaseUtils.getNetworkProperty(getNetworkNameByNetwork(network), "pmlog");
        if (pmlog != null && getChannelbyName(network, pmlog.getValue(NETWORKPROPERTY.VALUE)) != null)
            sendMessage(network, getChannelbyName(network, pmlog.getValue(NETWORKPROPERTY.VALUE)), message, "");
    }
}

