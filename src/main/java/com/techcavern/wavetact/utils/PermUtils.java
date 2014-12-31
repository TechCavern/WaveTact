package com.techcavern.wavetact.utils;

import com.techcavern.wavetact.utils.olddatabaseUtils.PermChannelUtils;
import com.techcavern.wavetact.objects.AuthedUser;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.events.WhoisEvent;
import static com.techcavern.wavetactdb.Tables.*;


public class PermUtils {

    public static String getAccount(PircBotX network, String userObject, String hostmask) { //gets account of user using hostmask
        String authtype = databaseUtils.getServer(IRCUtils.getNetworkNameByNetwork(network)).getValue(SERVERS.AUTHTYPE);
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

    public static String authUser(PircBotX network, String userObject) { //gets hostmask of userObject and calls getAccount using it
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

    public static String getNickServAccountName(PircBotX network, String userObject, String hostmask) { //calls getAccoutName() IF its not already found in get AuthedUser
        String userString = getAuthedUser(network, userObject, hostmask);
        if (userString == null) {
            userString = getAccountName(network, userObject);
            if (userString != null)
                Registry.AuthedUsers.add(new AuthedUser(IRCUtils.getNetworkNameByNetwork(network), userString, hostmask));
            IRCUtils.getNetworkNameByNetwork(network);
        }
        return userString;
    }

    public static String getAuthedUser(PircBotX network, String userObject, String hostmask) { //gets Authenticated Account Name found in the Authed User db.
        String userString = null;
        for (AuthedUser user : Registry.AuthedUsers) {
            if (user.getAuthHostmask().equals(hostmask) && user.getAuthNetwork().equals(IRCUtils.getNetworkNameByNetwork(network))) {
                userString = user.getAuthAccount();
            }
        }
        if (hostmask == null) {
            return userObject;
        } else {
            return userString;
        }

    }

    public static AuthedUser getAuthedUser(PircBotX network, String userObject) { //gets Authenticated User found in Authed User db
        String hostmask = IRCUtils.getIRCHostmask(network, userObject);
        for (AuthedUser user : Registry.AuthedUsers) {
            if (user.getAuthHostmask().equals(hostmask) && user.getAuthNetwork().equals(IRCUtils.getNetworkNameByNetwork(network))) {
                return user;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static String getAccountName(PircBotX network, String userObject) { //gets the actual NickServ ACcount Name
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

    private static int getAutomaticPermLevel(User userObject, Channel channelObject) { //gets the Auto Detected Perm Level
        if (userObject.isIrcop() && IRCUtils.getNetAdminAccess(userObject.getBot()).equalsIgnoreCase("true")) {
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

    private static int getManualPermLevel(PircBotX network, Channel channelObject, String account) { //gets Manual Perm Level using the account name
        if (account != null) {
            if (IRCUtils.getControllerByNick(account) != null) {
                return 9001;
            }
            if (IRCUtils.getNetworkAdminByNick(account, IRCUtils.getNetworkNameByNetwork(network)) != null) {
                return 20;
            }
            if (PermChannelUtils.getPermLevelChannel(IRCUtils.getNetworkNameByNetwork(network), account, channelObject.getName()) != null) {
                return PermChannelUtils.getPermLevelChannel(IRCUtils.getNetworkNameByNetwork(network), account, channelObject.getName()).getPermLevel();
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    public static int getPermLevel(PircBotX network, String userObject, Channel channelObject) { //gets the permlevel of the user in question
        String auth = PermUtils.authUser(network, userObject);
        if (auth != null) {
            return getLevel(network, userObject, channelObject, auth);
        } else {
            return 0;
        }
    }

    public static int getLevel(PircBotX network, String userObject, Channel channelObject, String account) { //gets the actual Perm Level
        if (channelObject != null) {
            int mpermlevel = getManualPermLevel(network, channelObject, account);
            User user = IRCUtils.getUserByNick(network, userObject);
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
                if (IRCUtils.getControllerByNick(account) != null) {
                    return 9001;
                } else if (IRCUtils.getNetworkAdminByNick(account, IRCUtils.getNetworkNameByNetwork(network)) != null) {
                    return 20;
                } else {
                    return 3;
                }
            } else {
                return 3;
            }
        }
    }

    public static boolean checkIfAccountEnabled(PircBotX network) { //checks if account authentication is enabled
        return IRCUtils.getAuthType(network).equalsIgnoreCase("account");
    }
}
