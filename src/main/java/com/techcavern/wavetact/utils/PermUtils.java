package com.techcavern.wavetact.utils;

import com.techcavern.wavetact.utils.databaseUtils.PermChannelUtils;
import com.techcavern.wavetact.utils.objects.AuthedUser;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.events.WhoisEvent;

public class PermUtils {

    public static AuthedUser getAuthedUser(PircBotX bot, String userObject){
        //TODO: WaitForFix;
        return null;
    }

    @SuppressWarnings("unchecked")
    public static String getAccount(PircBotX bot, String userObject) {
        String userString;
        WhoisEvent whois = IRCUtils.WhoisEvent(bot, userObject);
        if (whois != null) {
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

    private static int getManualPermLevel(PircBotX bot, String userObject, Channel channelObject) {
        String account = getAccount(bot, userObject);
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
    public static int getPermLevel(PircBotX bot, String userObject, Channel channelObject) {
        if (channelObject != null) {
            int mpermlevel = getManualPermLevel(bot, userObject, channelObject);
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
            String account = getAccount(bot, userObject);
            if (account != null) {
                if (GetUtils.getControllerByNick(account) != null) {
                    return 9001;
                } else if (GetUtils.getGlobalByNick(account, bot.getServerInfo().getServerName()) != null) {
                    return 20;
                } else {
                    return 2;
                }
            } else {
                return 2;
            }
        }
    }
}
