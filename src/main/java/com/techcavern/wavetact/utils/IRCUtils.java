package com.techcavern.wavetact.utils;

import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.WaitForQueue;
import org.pircbotx.hooks.events.WhoisEvent;
import org.pircbotx.output.OutputChannel;
import org.pircbotx.output.OutputUser;

/**
 * Created by jztech101 on 7/4/14.
 */
public class IRCUtils {
    public static void setMode(Channel channelObject, PircBotX botObject, String modeToSet, String hostmask) {
        OutputChannel o = new OutputChannel(botObject, channelObject);
        if (hostmask != null) {
            modeToSet = modeToSet + hostmask;
            o.setMode(modeToSet);
        } else {
            o.setMode(modeToSet);
        }
    }

    public static WhoisEvent<PircBotX> WhoisEvent(PircBotX bot, String userObject) {

        WhoisEvent<PircBotX> WhoisEvent;
        bot.sendIRC().whois(userObject);
        WaitForQueue waitForQueue = new WaitForQueue(bot);
        try {
            WhoisEvent = waitForQueue.waitFor(WhoisEvent.class);
            waitForQueue.close();
        } catch (InterruptedException | NullPointerException ex) {
            ex.printStackTrace();
            WhoisEvent = null;
        }
        if(!WhoisEvent.isExists()){
            return null;
        }
        return WhoisEvent;
    }

    public static void SendNotice(PircBotX botObject, User userObject, String notice) {
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

    public static String getHostmask(PircBotX bot, String userObject, boolean isBanmask) {
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
                hostmask = "*!" + Login + "@" + hostname;
            }
            hostmask = hostmask.replace(" ", "");
        } else {
            hostmask = null;
        }
        return hostmask;
    }
    public static String getIRCHostmask(PircBotX bot, String userObject) {
        User whois = GetUtils.getUserByNick(bot, userObject);
        String hostmask = "";
        if (whois != null) {
            String hostname = whois.getHostmask();
            String Login = whois.getLogin();
                hostmask = "*!" + Login + "@" + hostname;
            hostmask = hostmask.replace(" ", "");
        } else {
            hostmask = null;
        }
        return hostmask;
    }
    public static String getHost(PircBotX bot, String userObject) {
        String host;
        WhoisEvent whois = WhoisEvent(bot, userObject);
        if (whois != null) {
            host = whois.getHostname();
        } else {
            host = null;
        }
        return host;
    }
    public static void sendError(User user, String error){
        user.send().notice(error);
    }
    public static void processMessage(GenericCommand Command, PircBotX Bot, Channel channel, User user, int UserPermLevel, String[] messageParts, boolean isPrivate){
        if (Command.getPermLevel() == 9) {
            if (channel.isOp(Bot.getUserBot()) || channel.isOp(Bot.getUserBot()) || channel.isOwner(Bot.getUserBot())) {
                try {
                    Command.onCommand(user, Bot, channel, isPrivate, UserPermLevel, messageParts);
                } catch (Exception e) {
                    IRCUtils.sendError(user, "Unable to perform command - please make sure are using the correct syntax");
                }
            } else {
                IRCUtils.sendError(user, "Error: I must be at op or higher to perform the operation requested");
            }
        } else if (Command.getPermLevel() == 14) {

            if (channel.isOwner(Bot.getUserBot())) {
                try {
                    Command.onCommand(user, Bot, channel, isPrivate, UserPermLevel, messageParts);
                } catch (Exception e) {
                    IRCUtils.sendError(user, "Unable to perform command - please make sure are using the correct syntax");
                }
            } else {
                IRCUtils.sendError(user, "Error: I must be owner to perform the operation requested");
            }
        } else if (Command.getPermLevel() == 6) {
            if (channel.isOwner(Bot.getUserBot()) || channel.isOp(Bot.getUserBot()) || channel.isHalfOp(Bot.getUserBot()) || channel.isSuperOp(Bot.getUserBot())) {
                try {
                    Command.onCommand(user, Bot, channel, isPrivate, UserPermLevel, messageParts);
                } catch (Exception e) {
                    IRCUtils.sendError(user, "Unable to perform command - please make sure are using the correct syntax");
                }
            } else {
                if (Bot.getServerInfo().getPrefixes().contains("h")) {
                    IRCUtils.sendError(user, "Error: I must be half-op or higher to perform the operation requested");
                } else {
                    IRCUtils.sendError(user, "Error: I must be op or higher to perform the operation requested");
                }
            }
        } else {
            try {
                Command.onCommand(user, Bot, channel, isPrivate, UserPermLevel, messageParts);
            } catch (Exception e) {
                e.printStackTrace();
                IRCUtils.sendError(user, "Unable to perform command - please make sure are using the correct syntax");
            }
        }
    }

}
