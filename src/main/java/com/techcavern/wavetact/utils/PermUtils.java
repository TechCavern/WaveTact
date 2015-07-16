package com.techcavern.wavetact.utils;

import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.events.WhoisEvent;

import static com.techcavern.wavetactdb.Tables.*;


public class PermUtils {

    public static String getAccount(PircBotX network, String userObject, String hostmask) { //gets account of user using hostmask
        String authtype = DatabaseUtils.getServer(IRCUtils.getNetworkNameByNetwork(network)).getValue(SERVERS.AUTHTYPE);
        switch (authtype) {
            case "nickserv":
                return getNickServAccountName(network, userObject, hostmask);
            case "account":
                return getAuthedUser(network, hostmask);
            default:
                return hostmask;
        }
    }

    public static String authUser(PircBotX network, String userObject) { //gets hostmask of userObject and calls getAccount using it
        String hostmask = IRCUtils.getHostmask(network, userObject, true);
        if (hostmask != null) {
            return getAccount(network, userObject, hostmask);
        } else {
            return null;
        }
    }

    public static String getNickServAccountName(PircBotX network, String userObject, String hostmask) { //calls getAccoutName() IF its not already found in get AuthedUser
        String userString = getAuthedUser(network, hostmask);
        if (userString == null) {
            userString = getAccountName(network, userObject);
            if (userString != null)
                Registry.authedUsers.get(network).put(hostmask, userString);
        }
        return userString;
    }

    public static String getAuthedUser(PircBotX network, String hostmask) { //gets Authenticated Account Name found in the Authed User db.
        String userString = Registry.authedUsers.get(network).get(hostmask);
        if (hostmask == null) {
            return null;
        } else {
            return userString;
        }

    }

    @SuppressWarnings("unchecked")
    public static String getAccountName(PircBotX network, String userObject) { //gets the actual NickServ ACcount Name
        WhoisEvent whois = IRCUtils.WhoisEvent(network, userObject, true);
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
        if (userObject.isIrcop() && DatabaseUtils.getServer(IRCUtils.getNetworkNameByNetwork(userObject.getBot())).getValue(SERVERS.NETWORKADMINACCESS)) {
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

    private static int getManualPermLevel(String userObject, PircBotX network, Channel channelObject, String account) { //gets Manual Perm Level using the account name
        if (isIgnored(IRCUtils.getHostmask(network, userObject, true), IRCUtils.getNetworkNameByNetwork(network))) {
            return -2;
        } else if (account != null) {
            String channelName = null;
            if (channelObject != null) {
                channelName = channelObject.getName();
            }
            if (isNetworkAdmin(account, IRCUtils.getNetworkNameByNetwork(network))) {
                return 20;
            } else if (DatabaseUtils.getNetworkUserProperty(IRCUtils.getNetworkNameByNetwork(network), account, "permlevel") != null) {
                int permlevel = 1;
                try {
                    permlevel = Integer.parseInt(DatabaseUtils.getNetworkUserProperty(IRCUtils.getNetworkNameByNetwork(network), account, "permlevel").getValue(NETWORKUSERPROPERTY.VALUE));
                } catch (Exception e) {
                }
                if (permlevel > 18) {
                    permlevel = 18;
                }
                return permlevel;
            } else if (DatabaseUtils.getChannelUserProperty(IRCUtils.getNetworkNameByNetwork(network), channelName, account, "permlevel") != null) {
                int permlevel = 1;
                try {
                    permlevel = Integer.parseInt(DatabaseUtils.getChannelUserProperty(IRCUtils.getNetworkNameByNetwork(network), channelName, account, "permlevel").getValue(CHANNELUSERPROPERTY.VALUE));
                } catch (Exception e) {
                }
                if (permlevel > 18) {
                    permlevel = 18;
                }
                return permlevel;
            } else {
                return 1;
            }
        } else {
            return 0;
        }
    }

    public static int getPermLevel(PircBotX network, String userObject, Channel channelObject) { //gets the permlevel of the user in question
        String auth = PermUtils.authUser(network, userObject);
        return getLevel(network, userObject, channelObject, auth);
    }

    public static int getLevel(PircBotX network, String userObject, Channel channelObject, String account) { //gets the actual Perm Level
        if (channelObject != null) {
            int mpermlevel = getManualPermLevel(userObject, network, channelObject, account);
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
            return getManualPermLevel(userObject, network, channelObject, account);
        }
    }

    public static boolean isAccountEnabled(PircBotX network) { //checks if account authentication is enabled
        return DatabaseUtils.getServer(IRCUtils.getNetworkNameByNetwork(network)).getValue(SERVERS.AUTHTYPE).equalsIgnoreCase("account");
    }

    public static boolean isNetworkAdmin(String account, String network) {
        for (String c : StringUtils.split(DatabaseUtils.getServer(network).getValue(SERVERS.NETWORKADMINS), ", ")) {
            if (c.equalsIgnoreCase(account))
                return true;
        }
        return false;
    }

    public static boolean isIgnored(String hostmask, String network) {
        if (DatabaseUtils.getNetworkProperty(network, "ignoredhosts") == null) {
            return false;
        } else
            for (String c : StringUtils.split(DatabaseUtils.getNetworkProperty(network, "ignoredhosts").getValue(NETWORKPROPERTY.VALUE), ", ")) {
                if (c.equalsIgnoreCase(hostmask))
                    return true;
            }
        return false;
    }
}
