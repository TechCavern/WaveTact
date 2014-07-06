package com.techcavern.wavetact.utils;

import com.techcavern.wavetact.utils.objects.PermUser;
import com.techcavern.wavetact.utils.objects.objectUtils.PermUserUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.WaitForQueue;
import org.pircbotx.hooks.events.WhoisEvent;

public class PermUtils {

    @SuppressWarnings("unchecked")
    public static String getAccount(PircBotX bot, User userObject) {
        String userString;
        if(userObject != null) {
            bot.sendRaw().rawLineNow("WHOIS " + userObject.getNick());
        }else{
            userString = null;
        }
        WaitForQueue waitForQueue = new WaitForQueue(bot);
        WhoisEvent<PircBotX> test;
        try {
            test = waitForQueue.waitFor(WhoisEvent.class);
            waitForQueue.close();
            userString = test.getRegisteredAs();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
            userString = null;
        }

        return userString;
    }
    private static boolean isAuthor(PircBotX bot, User userObject) {
        String account = getAccount(bot, userObject);
        if (account != null) {
            for(String author: GeneralRegistry.Authors){
                if(author.equals(account)){
                    return true;
                }
            }
        } else {
            return false;
        }
        return false;
    }

    public static int getAutomaticPermLevel(PircBotX bot, User userObject, Channel channelObject) {

        if(userObject.isIrcop()){
            return 20;
        }
        else if (channelObject.isOwner(userObject)) {
            return 15;
        }else if(channelObject.isSuperOp(userObject)){
            return 13;
        } else if (channelObject.isOp(userObject)) {
            return 10;
        }else if (channelObject.isHalfOp(userObject)){
            return 7;
        } else if (channelObject.hasVoice(userObject) || isAuthor(bot, userObject)) {
            return 5;
        } else {
            return 0;
        }
    }
    public static int getManualPermLevel(PircBotX bot, User userObject, Channel channelObject) {
        String account = getAccount(bot, userObject);
        if(account != null) {
            for(String c: GeneralRegistry.Controllers){
                if(c.equals(account))
                    return 9001;
            }
            if(PermUserUtils.getPermUserbyNick(account, bot.getServerInfo().getNetwork()).getisGlobal()){
                return 20;
            }else{
                if(PermUserUtils.getPermLevelChannel(bot.getServerInfo().getNetwork(), account,channelObject.getName()) != null) {
                    return PermUserUtils.getPermLevelChannel(bot.getServerInfo().getNetwork(), account, channelObject.getName()).getPermLevel();
                }else{
                    return 0;
                }
            }

        }else{
            return 0;
        }

    }
    public static int getPermLevel(PircBotX bot, User userObject, Channel channelObject){

        if(getAutomaticPermLevel(bot, userObject, channelObject) < getManualPermLevel(bot,userObject, channelObject)){
            return getManualPermLevel(bot, userObject, channelObject);
        }else{
            return getAutomaticPermLevel(bot, userObject, channelObject);
        }
    }
}
