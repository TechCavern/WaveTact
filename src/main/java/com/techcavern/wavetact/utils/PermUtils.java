package com.techcavern.wavetact.utils;

import com.techcavern.wavetact.utils.databaseUtils.PermChannelUtils;
import com.techcavern.wavetact.utils.objects.AuthedUser;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.events.WhoisEvent;

public class PermUtils {

    public static String getAccount(PircBotX network, String userObject, String hostmask) {
        String authtype = GetUtils.getAuthType(network);
        if (authtype == null) {
            return userObject;
        }
        switch (authtype) {
            case "nickserv":
                return getNickServAccountName(network, userObject, hostmask);
            case "account":
                return getAuthedUser(network, userObject, hostmask);
            default:
                return userObject;
        }
    }

    public static String authUser(PircBotX network, String userObject) {
        String hostmask = IRCUtils.getIRCHostmask(network, userObject);
        if (hostmask != null) {
            return getAccount(network, userObject, hostmask);
        } else {
            hostmask = IRCUtils.getHostmask(network, userObject, false);
            if (hostmask != null) {
                return getAccount(network, userObject, hostmask);
            } else {
                return null;
            }
        }
    }

    public static String getNickServAccountName(PircBotX network, String userObject, String hostmask) {
        String userString = getAuthedUser(network, userObject, hostmask);
        if (userString == null) {
            userString = getAccountName(network, userObject);
            if (userString != null)
                Constants.AuthedUsers.add(new AuthedUser(network.getServerInfo().getNetwork(), userString, hostmask));
        }
        return userString;
    }

    public static String getAuthedUser(PircBotX network, String userObject, String hostmask) {
        String userString = null;
        for (AuthedUser user : Constants.AuthedUsers) {
            if (user.getAuthHostmask().equals(hostmask) && user.getAuthNetwork().equals(network.getServerInfo().getNetwork())) {
                userString = user.getAuthAccount();
            }
        }
        if (hostmask == null) {
            return userObject;
        } else {
            return userString;
        }

    }

    public static AuthedUser getAuthedUser(PircBotX network, String userObject) {
        String hostmask = IRCUtils.getIRCHostmask(network, userObject);
        for (AuthedUser user : Constants.AuthedUsers) {
            if (user.getAuthHostmask().equals(hostmask) && user.getAuthNetwork().equals(network.getServerInfo().getNetwork())) {
                return user;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static String getAccountName(PircBotX network, String userObject) {
        WhoisEvent whois = IRCUtils.WhoisEvent(network, userObject);
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
        if (userObject.isIrcop() && GetUtils.getNetAdminAccess(userObject.getBot()).equalsIgnoreCase("true")) {
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

    private static int getManualPermLevel(PircBotX network, Channel channelObject, String account) {
        if (account != null) {
            if (GetUtils.getControllerByNick(account) != null) {
                return 9001;
            }
            if (GetUtils.getNetworkAdminByNick(account, network.getServerInfo().getNetwork()) != null) {
                return 20;
            }
            if (PermChannelUtils.getPermLevelChannel(network.getServerInfo().getNetwork(), account, channelObject.getName()) != null) {
                return PermChannelUtils.getPermLevelChannel(network.getServerInfo().getNetwork(), account, channelObject.getName()).getPermLevel();
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    public static int getPermLevel(PircBotX network, String userObject, Channel channelObject) {
        String auth = PermUtils.authUser(network, userObject);
        if (auth != null) {
            return getLevel(network, userObject, channelObject, auth);
        } else {
            return getLevel(network, userObject, channelObject, userObject);
        }
    }

    public static int getLevel(PircBotX network, String userObject, Channel channelObject, String account) {
        if (channelObject != null) {
            int mpermlevel = getManualPermLevel(network, channelObject, account);
            User user = GetUtils.getUserByNick(network, userObject);
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
                } else if (GetUtils.getNetworkAdminByNick(account, network.getServerInfo().getNetwork()) != null) {
                    return 20;
                } else {
                    return 3;
                }
            } else {
                return 3;
            }
        }
    }

    public static boolean checkIfAccountEnabled(PircBotX network) {
        return GetUtils.getAuthType(network).equalsIgnoreCase("account");
    }
}
