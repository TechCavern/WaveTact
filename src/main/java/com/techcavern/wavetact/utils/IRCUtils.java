package com.techcavern.wavetact.utils;

import org.pircbotz.Channel;
import org.pircbotz.PircBotZ;
import org.pircbotz.User;
import org.pircbotz.hooks.WaitForQueue;
import org.pircbotz.hooks.events.WhoisEvent;
import org.pircbotz.output.OutputChannel;
import org.pircbotz.output.OutputUser;

/**
 * Created by jztech101 on 7/4/14.
 */
public class IRCUtils {
    public static void setMode(Channel channelObject, PircBotZ botObject, String modeToSet, String hostmask) {
        OutputChannel o = new OutputChannel(botObject, channelObject);
        if (hostmask != null) {
            modeToSet = modeToSet + hostmask;
            o.setMode(modeToSet);
        } else {
            o.setMode(modeToSet);
        }
    }

    public static WhoisEvent WhoisEvent(PircBotZ bot, String userObject) {
        WhoisEvent WhoisEvent;
        if (userObject != null) {
            bot.sendRaw().rawLineNow("WHOIS " + userObject);
        } else {
            WhoisEvent = null;
        }
        WaitForQueue waitForQueue = new WaitForQueue(bot);
        try {
            while (true) {
                WhoisEvent event = waitForQueue.waitFor(WhoisEvent.class);
                if (!event.getNick().equals(userObject))
                    continue;
                waitForQueue.close();
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
            WhoisEvent = null;
        }
        return WhoisEvent;
    }

    public static void SendNotice(PircBotZ botObject, User userObject, String notice) {
        OutputUser x = new OutputUser(botObject, userObject);
        x.notice(notice);
    }

    public static void SendMessage(User userObject, Channel channelObject, String message, boolean isPrivate) {
        if (channelObject != null && !isPrivate) {
            channelObject.send().message(message);
        } else {
            userObject.send().message(message);
        }
    }

    public static void SendAction(User userObject, Channel channelObject, String message, boolean isPrivate) {
        if (channelObject != null && !isPrivate) {
            channelObject.send().action(message);
        } else {
            userObject.send().action(message);
        }
    }

    public static String getHostmask(PircBotZ bot, String userObject, boolean isBanmask) {
        String hostmask;
        WhoisEvent whois = WhoisEvent(bot, userObject);
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
                hostmask = userObject + "!" + Login + "@" + hostname;
            }
            hostmask = hostmask.replace(" ", "");
        } else {
            hostmask = null;
        }
        return hostmask;
    }

}
