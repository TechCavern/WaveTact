package com.techcavern.wavetact.utils;

import com.techcavern.wavetact.utils.databaseUtils.PermChannelUtils;
import com.techcavern.wavetact.utils.objects.AuthedUser;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.events.WhoisEvent;

public class PermUtils {

    public static String getPrivateAccount(PircBotX bot, String userObject, String hostmask) {
        String authtype = GetUtils.getAuthType(bot);
        switch (authtype) {
            case "nickserv":
                return getAuthedNickServUser(bot, userObject, hostmask);
            case "account":
                return getAuthedUser(bot, userObject, hostmask);
            default:
                return userObject;
        }
    }

    public static String getAuthedAccount(PircBotX bot, String userObject) {
        String hostmask = IRCUtils.getIRCHostmask(bot, userObject);
        if (hostmask != null) {
            return getPrivateAccount(bot, userObject, hostmask);
        } else {
            hostmask = IRCUtils.getHostmask(bot, userObject, false);
            if (hostmask != null) {
                return getPrivateAccount(bot, userObject, hostmask);
            } else {
                return null;
            }
        }
    }

    public static String getAuthedNickServUser(PircBotX bot, String userObject, String hostmask) {
        String userString = getAuthedUser(bot, userObject, hostmask);
        if (userString == null) {
            userString = getAccountName(bot, userObject);
            if (userString != null)
                GeneralRegistry.AuthedUsers.add(new AuthedUser(bot.getServerInfo().getNetwork(), userString, hostmask));
        }
        return userString;
    }

    public static String getAuthedUser(PircBotX bot, String userObject, String hostmask) {
        String userString = null;
        for (AuthedUser user : GeneralRegistry.AuthedUsers) {
            if (user.getAuthHostmask().equals(hostmask) && user.getAuthNetwork().equals(bot.getServerInfo().getNetwork())) {
                userString = user.getAuthAccount();
            }
        }
        if (hostmask == null) {
            return userObject;
        } else {
            return userString;
        }

    }

    public static AuthedUser getAuthUser(PircBotX bot, String userObject) {
        String hostmask = IRCUtils.getIRCHostmask(bot, userObject);
        for (AuthedUser user : GeneralRegistry.AuthedUsers) {
            if (user.getAuthHostmask().equals(hostmask) && user.getAuthNetwork().equals(bot.getServerInfo().getNetwork())) {
                return user;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static String getAccountName(PircBotX bot, String userObject) {
        WhoisEvent whois = IRCUtils.WhoisEvent(bot, userObject);
        String userString;
        if (whois != null) {
            userString = whois.getRegisteredAs();
            if (userString != null) {
                userString = userString.toLowerCase();
                if (userString.isEmpty()) {
                    userString = userObject.toLowerCase();
                }
            } else {
                userString = null;
            }
        } else {
            userString = null;
        }
        return userString;
    }

    private static int getAutomaticPermLevel(User userObject, Channel channelObject) {
        if (userObject.isIrcop()) {
            return 20;
        } else if (channelObject.isOwner(userObject)) {
            return 15;
        } else if (channelObject.isSuperOp(userObject)) {
            return 13;
        } else if (channelObject.isOp(userObject)) {
            return 10;
        } else if (channelObject.isHalfOp(userObject)) {
            return 7;
        } else if (channelObject.hasVoice(userObject)) {
            return 5;
        } else {
            return 0;
        }
    }

    private static int getManualPermLevel(PircBotX bot, Channel channelObject, String account) {
        if (account != null) {
            if (GetUtils.getControllerByNick(account) != null) {
                return 9001;
            }
            if (GetUtils.getNetworkAdminByNick(account, bot.getServerInfo().getNetwork()) != null) {
                return 20;
            }
            if (PermChannelUtils.getPermLevelChannel(bot.getServerInfo().getNetwork(), account, channelObject.getName()) != null) {
                return PermChannelUtils.getPermLevelChannel(bot.getServerInfo().getNetwork(), account, channelObject.getName()).getPermLevel();
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    public static int getPermLevel(PircBotX bot, String userObject, Channel channelObject) {
        String auth = PermUtils.getAuthedAccount(bot, userObject);
        if (auth != null) {
            return getAuthPermLevel(bot, userObject, channelObject, auth);
        } else {
            return getAuthPermLevel(bot, userObject, channelObject, userObject);
        }
    }

    public static int getAuthPermLevel(PircBotX bot, String userObject, Channel channelObject, String account) {
        if (channelObject != null) {
            int mpermlevel = getManualPermLevel(bot, channelObject, account);
            User user = GetUtils.getUserByNick(bot, userObject);
            int apermlevel = 0;
            if (user != null) {
                apermlevel = getAutomaticPermLevel(user, channelObject);
            }
            if (mpermlevel < 0) {
                return mpermlevel;
            } else if (apermlevel < mpermlevel) {
                return mpermlevel;
            } else {
                return apermlevel;
            }
        } else {
            if (account != null) {
                if (GetUtils.getControllerByNick(account) != null) {
                    return 9001;
                } else if (GetUtils.getNetworkAdminByNick(account, bot.getServerInfo().getNetwork()) != null) {
                    return 20;
                } else {
                    return 3;
                }
            } else {
                return 3;
            }
        }
    }

    public static boolean checkIfAccountEnabled(PircBotX bot) {
        return GetUtils.getAuthType(bot).equalsIgnoreCase("account");
    }
}
