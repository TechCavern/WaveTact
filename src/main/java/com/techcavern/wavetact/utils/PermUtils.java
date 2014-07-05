package com.techcavern.wavetact.utils;

import com.techcavern.wavetact.utils.objects.PermUser;
import com.techcavern.wavetact.utils.objects.PermUserHostmask;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.WaitForQueue;
import org.pircbotx.hooks.events.WhoisEvent;

public class PermUtils {

    @SuppressWarnings("unchecked")
    private static String getAccount(PircBotX bot, User userObject) {
        String userString;
        bot.sendRaw().rawLineNow("WHOIS " + userObject.getNick());
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
            return GeneralRegistry.Authors.equals(account);
        } else {
            return GeneralRegistry.AuthorHostmasks.equals("*!" + userObject.getRealName() +"@" + userObject.getHostmask());
        }
    }

    public static int getAutomaticPermLevel(PircBotX bot, User userObject, Channel channelObject) {

        if (channelObject.isOwner(userObject)) {
            return 15;
        } else if (channelObject.isOp(userObject) || channelObject.isSuperOp(userObject)) {
            return 10;
        }else if (channelObject.isHalfOp(userObject)){
            return 7;
        } else if (channelObject.hasVoice(userObject) || isAuthor(bot, userObject)) {
            return 5;
        } else {
            return 0;
        }
    }
    public static int getManualPermLevel(PircBotX bot, User userObject) {
        String account = getAccount(bot, userObject);
        if(account != null) {
            for(PermUser user: GeneralRegistry.PermUsers){
                if(user.getPermUser().equals(account)){
                    return user.getPermLevel();
                }

            }
        }else{
            for(PermUserHostmask hostmask : GeneralRegistry.PermUserHostmasks){
                if((hostmask.getHostmask().equals(userObject.getHostmask()) || hostmask.getHostmask().equals("*")) &&(hostmask.getRealname().equalsIgnoreCase(userObject.getRealName()) || hostmask.getRealname().equals("*"))){
                    return hostmask.getPermLevel();
                }
            }
        }
        return 0;

    }
    public static int getPermLevel(PircBotX bot, User userObject, Channel channelObject){
        if(getAutomaticPermLevel(bot, userObject, channelObject) < getManualPermLevel(bot, userObject)){
            return getManualPermLevel(bot, userObject);
        }else{
            return getAutomaticPermLevel(bot, userObject, channelObject);
        }
    }
}
