package com.techcavern.wavetact.utils;

import com.techcavern.wavetact.objects.IRCCommand;
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

import static com.techcavern.wavetactdb.Tables.*;


public class IRCUtils {
    public static void setMode(Channel channelObject, PircBotX networkObject, String modeToSet, String hostmask) {
        if (hostmask != null) {
            Registry.messageQueue.get(networkObject).add("MODE " + channelObject.getName() + " " + modeToSet + " " + hostmask);
        } else {
            Registry.messageQueue.get(networkObject).add("MODE " + channelObject.getName() + " " + modeToSet);
        }
    }

    public static WhoisEvent WhoisEvent(PircBotX network, String userObject, boolean useCache) {
        WhoisEvent WhoisEvent = Registry.whoisEventCache.get(network).get(userObject);
        if (useCache) {
            if (WhoisEvent != null) {
                return WhoisEvent;
            } else {
                WhoisEvent = Registry.whoisEventCache.get(network).get(userObject);
                if (WhoisEvent != null) {
                    return WhoisEvent;
                } else {
                    if (Registry.lastWhois.get(network).equalsIgnoreCase(userObject)) {
                        int i = 0;
                        while (WhoisEvent == null && i < 10) {
                            try {
                                TimeUnit.MILLISECONDS.sleep(100);
                            } catch (Exception e) {
                            }
                            WhoisEvent = Registry.whoisEventCache.get(network).get(userObject);
                            i++;
                        }
                        if (WhoisEvent != null)
                            return WhoisEvent;
                    }
                }
            }
        } else if (WhoisEvent != null) {
            Registry.whoisEventCache.get(network).remove(WhoisEvent);
        }
        Registry.lastWhois.put(network, userObject);
        WaitForQueue waitForQueue = new WaitForQueue(network);
        try {
            Registry.messageQueue.get(network).add("WHOIS " + userObject + " " + userObject);
            WhoisEvent = waitForQueue.waitFor(WhoisEvent.class, 2, TimeUnit.SECONDS);
            waitForQueue.close();
        } catch (InterruptedException | NullPointerException ex) {
            ex.printStackTrace();
            WhoisEvent = null;
        }
        if (WhoisEvent == null || !WhoisEvent.isExists() || !WhoisEvent.getNick().equalsIgnoreCase(userObject))
            return null;
        else
            Registry.whoisEventCache.get(network).put(userObject, WhoisEvent);
        return WhoisEvent;
    }

    public static void sendMessage(User userObject, PircBotX networkObject, Channel channelObject, String message, String prefix) {
        if (channelObject != null) {
            for (int i = 0; i < message.length(); i += 350) {
                String messageToSend = message.substring(i, Math.min(message.length(), i + 350));
                if (!messageToSend.isEmpty()) {
                    Registry.messageQueue.get(networkObject).add("PRIVMSG " + prefix + channelObject.getName() + " :" + messageToSend);
                    if (prefix.isEmpty())
                        sendRelayMessage(networkObject, channelObject, noPing(networkObject.getNick()) + ": " + messageToSend);
                }
            }
        } else {
            Registry.messageQueue.get(networkObject).add(("PRIVMSG " + userObject.getNick() + " :" + message));
        }
    }

    public static void sendKick(User userObject, User recipientObject, PircBotX networkObject, Channel channelObject, String message) {
        if (channelObject != null) {
            for (int i = 0; i < message.length(); i += 350) {
                String messageToSend = message.substring(i, Math.min(message.length(), i + 350));
                if (!messageToSend.isEmpty()) {
                    Registry.messageQueue.get(networkObject).add("KICK " + channelObject.getName() + " " + recipientObject.getNick() + " :" + messageToSend);
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
                Iterator iterator = Registry.networks.inverse().keySet().iterator();
                while (iterator.hasNext()) {
                    PircBotX net = (PircBotX) iterator.next();
                    if (net != networkObject) {
                        Record rec = DatabaseUtils.getNetworkProperty(IRCUtils.getNetworkNameByNetwork(net), "relaychan");
                        if (rec != null) {
                            String relaychan = rec.getValue(NETWORKPROPERTY.VALUE);
                            if (relaychan != null)
                                Registry.messageQueue.get(net).add("PRIVMSG " + relaychan + " :[" + getNetworkNameByNetwork(networkObject) + "] " + msg);
                        }
                    }
                }
            }
        }
    }

    public static void sendAction(User userObject, PircBotX networkObject, Channel channelObject, String message, String prefix) {
        if (channelObject != null) {
            Registry.messageQueue.get(networkObject).add("PRIVMSG " + prefix + channelObject.getName() + " :\u0001ACTION " + message + "\u0001");
            if (prefix.isEmpty())
                sendRelayMessage(networkObject, channelObject, "* " + noPing(networkObject.getNick()) + " " + message);
        } else {
            Registry.messageQueue.get(networkObject).add("PRIVMSG " + userObject.getNick() + " :\u0001ACTION " + message + "\u0001");
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
                hostmask =  getBanmask(hostname, Login);
            } else {
                hostmask = getLoginmask(hostname, Login);
            }
            hostmask = hostmask.replace(" ", "");
        } else {
            hostmask = null;
        }
        return hostmask;
    }

    public static String getBanmask(String hostname, String ident){
        String hostmask;
        if (!ident.startsWith("~")) {
            hostmask = "*!" + ident + "@" + hostname;
        } else {
            hostmask = "*!*@" + hostname;
        }
        return hostmask;
    }

    public static String getLoginmask(String hostname, String ident){
        return "*!" + ident + "@" + hostname;
    }


    public static void sendGlobal(String message, User user) {
        Iterator iterator = Registry.networks.inverse().keySet().iterator();
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
                hostmask =  getBanmask(hostname, Login);
            } else {
                hostmask = getLoginmask(hostname, Login);
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

    public static IRCCommand getCommand(String Command, String Network, String Channel) {
        IRCCommand cmd = getCustomCommand(Command, Network, Channel);
        if (cmd != null) {
            return cmd;
        } else {
            return Registry.ircCommands.get(Command);
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
                        if (g.contains("$")) {
                            char[] chars = g.toCharArray();
                            for (int v = 0; v < chars.length; v++) {
                                if (chars[v] == ("$").charAt(0) && chars[v + 1] != ("*").charAt(0)) {
                                    action = action.replace(String.valueOf(chars[v]) + String.valueOf(chars[v + 1]), args[Integer.valueOf(String.valueOf(chars[v + 1])) - 1]);
                                    try {
                                        if (Integer.valueOf(chars[v + 1]) > i) {
                                            i++;
                                        }
                                    } catch (Exception e) {
                                    }
                                }
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


    public static PircBotX getNetworkByNetworkName(String name) {
        return Registry.networks.get(name);
    }

    public static String getNetworkNameByNetwork(PircBotX network) {
        return Registry.networks.inverse().get(network);
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

    public static String noPing(String original) {
        char[] originChars = original.toCharArray();
        for (int i = 0; i < originChars.length; i++) {
            if (Registry.charReplacements.get(String.valueOf(originChars[i])) != null) {
                original = original.replaceFirst(String.valueOf(originChars[i]), Registry.charReplacements.get(String.valueOf(originChars[i])));
                break;
            }
        }
        return original;
    }

    public static void sendError(User userObject, PircBotX networkObject, Channel channelObject, String message, String prefix) {
        if (channelObject != null) {
            Record verbose = DatabaseUtils.getChannelProperty(IRCUtils.getNetworkNameByNetwork(networkObject), channelObject.getName(), "verboseerrors");
            if (verbose != null && verbose.getValue(CHANNELPROPERTY.VALUE).equalsIgnoreCase("true")) {
                sendMessage(userObject, networkObject, channelObject, message, prefix);
            } else {
                sendNotice(userObject, networkObject, null, message, prefix);
            }
        } else {
            sendMessage(userObject, networkObject, channelObject, message, prefix);
        }
    }

    public static void sendNotice(User userObject, PircBotX networkObject, Channel channelObject, String message, String prefix) {
        if (channelObject != null) {
            Registry.messageQueue.get(networkObject).add("NOTICE " + prefix + channelObject.getName() + " :" + message);
        } else {
            Registry.messageQueue.get(networkObject).add("NOTICE " + userObject.getNick() + " :" + message);
        }
    }
}

