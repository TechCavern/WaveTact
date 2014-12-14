package com.techcavern.wavetact.utils;

import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.WaitForQueue;
import org.pircbotx.hooks.events.WhoisEvent;
import org.pircbotx.output.OutputChannel;


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
            networkObject.sendRaw().rawLine("PRIVMSG " + prefix + channelObject.getName() + " :" + message);
        } else {
            userObject.send().message(message);
        }
    }

    public static void sendAction(User userObject, PircBotX networkObject, Channel channelObject, String message, String prefix) {
        if (channelObject != null) {
            networkObject.sendRaw().rawLine("PRIVMSG " + prefix + channelObject.getName() + " :" + (char) 1 + message + (char) 1);
        } else {
            userObject.send().action(message);
        }
    }

    public static String getPrefix(String fullChannelName) {
        String[] channel = fullChannelName.split("#");
        return channel[0];
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
        for (PircBotX network : GeneralRegistry.WaveTact.getBots()) {
            sendNetworkGlobal(message, network, user);
        }

    }

    public static void sendNetworkGlobal(String message, PircBotX network, User user) {
        for (Channel channel : network.getUserBot().getChannels()) {
            channel.send().notice("[" + user.getNick() + "]: " + message);
        }
    }

    public static String getIRCHostmask(PircBotX network, String userObject) {
        User whois = GetUtils.getUserByNick(network, userObject);
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

    public static void sendError(User user, String error) {
        user.send().notice(error);
    }

}
