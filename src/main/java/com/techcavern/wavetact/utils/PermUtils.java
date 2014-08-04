package com.techcavern.wavetact.utils;

import com.techcavern.wavetact.utils.databaseUtils.PermChannelUtils;
import com.techcavern.wavetact.utils.objects.AuthedUser;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.events.WhoisEvent;

public class PermUtils {

    public static String getPrivateAccount(PircBotX bot, String userObject, String hostmask){
        String authtype = GetUtils.getAuthType(bot);
        if(authtype.equals("nickserv"))
        return getAuthedNickServUser(bot, userObject, hostmask);
        else if(authtype.equals("account"))
        return getAuthedUser(bot, userObject, hostmask);
        else{
        return userObject;
        }
    }
    public static String getAuthedAccount(PircBotX bot, String userObject, boolean isPrivate){
        String hostmask = null;
        if(!isPrivate)
        hostmask = IRCUtils.getIRCHostmask(bot, userObject, false);
        else
        hostmask = IRCUtils.getHostmask(bot, userObject, false);
        if(hostmask != null)
        return getPrivateAccount(bot, userObject, hostmask);
        else
         return null;
    }
    public static String getAccount(PircBotX bot, String userObject){
        String hostmask = IRCUtils.getHostmask(bot, userObject, false);
        return getPrivateAccount(bot, userObject, hostmask);
    }
    public static String getAuthedNickServUser(PircBotX bot, String userObject, String hostmask){
        String userString = getAuthedUser(bot, userObject, hostmask);
        if(userString == null){
            userString = getAccountName(bot, userObject);
            GeneralRegistry.AuthedUsers.add(new AuthedUser(bot.getServerInfo().getServerName(),userString, hostmask ));
        }
        return userString;
    }

    public static String getAuthedUser(PircBotX bot, String userObject, String hostmask){
        String userString = null;
        for(AuthedUser user:GeneralRegistry.AuthedUsers){
                if(user.getAuthHostmask().equals(hostmask) && user.getAuthNetwork().equals(bot.getServerInfo().getServerName())){
                    userString = user.getAuthAccount();
                }
            }
        if(hostmask == null){
            return userObject;
        }else{
            return userString;
        }

    }
    public static AuthedUser getAuthUser(PircBotX bot, String userObject){
        String hostmask = IRCUtils.getIRCHostmask(bot, userObject, false);
        for(AuthedUser user:GeneralRegistry.AuthedUsers){
            if(user.getAuthHostmask().equals(hostmask) && user.getAuthNetwork().equals(bot.getServerInfo().getServerName())){
                return user;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static String getAccountName(PircBotX bot, String userObject) {
        WhoisEvent whois = IRCUtils.WhoisEvent(bot, userObject);
        String userString;
        if (whois.getNick() != null) {
            userString = whois.getRegisteredAs();
            if(userString != null){
                userString = userString.toLowerCase();
                if (userString.isEmpty()){
                    userString = userObject.toLowerCase();
                }
            }
        } else {
            userString = userObject;
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

    private static int getManualPermLevel(PircBotX bot, String userObject, Channel channelObject, boolean isPrivate) {
        String account = getAuthedAccount(bot, userObject, isPrivate);
        if (account != null) {
            if (GetUtils.getControllerByNick(account) != null) {
                return 9001;
            }
            if (GetUtils.getGlobalByNick(account, bot.getServerInfo().getServerName()) != null) {
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
    public static int getPermLevel(PircBotX bot, String userObject, Channel channelObject, boolean isPrivate) {
        if (channelObject != null) {
            int mpermlevel = getManualPermLevel(bot, userObject, channelObject, isPrivate);
            User user = GetUtils.getUserByNick(bot,userObject);
            int apermlevel = 0;
            if(user != null) {
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
            String account = getAuthedAccount(bot, userObject, isPrivate);
            if (account != null) {
                if (GetUtils.getControllerByNick(account) != null) {
                    return 9001;
                } else if (GetUtils.getGlobalByNick(account, bot.getServerInfo().getServerName()) != null) {
                    return 20;
                } else {
                    return 3;
                }
            } else {
                return 3;
            }
        }
    }
    public static boolean checkIfAccountEnabled(PircBotX bot){
        if(GetUtils.getAuthType(bot).equalsIgnoreCase("account")){
            return true;
        }else{
            return false;
        }
    }
}
