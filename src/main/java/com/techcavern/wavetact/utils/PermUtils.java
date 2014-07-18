package com.techcavern.wavetact.utils;

import com.techcavern.wavetact.utils.databaseUtils.PermChannelUtils;
import com.techcavern.wavetact.utils.objects.Global;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.WaitForQueue;
import org.pircbotx.hooks.events.WhoisEvent;

public class PermUtils {

    @SuppressWarnings("unchecked")
    public static String getAccount(PircBotX bot, String userObject) {
        String userString;
        if(userObject != null) {
            bot.sendRaw().rawLineNow("WHOIS " + userObject);
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
        if(userString == null || userString.isEmpty()){
            if(GetUtils.getUserByNick(bot, userObject).isVerified()){
                userString = userObject;
            }
        }
        return userString;
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
        } else if (channelObject.hasVoice(userObject)) {
            return 5;
        } else {
            return 0;
        }
    }
    public static int getManualPermLevel(PircBotX bot, String userObject, Channel channelObject) {
        String account = getAccount(bot, userObject);
        if(account != null) {
            if(GetUtils.getControllerByNick(account) != null){
                return 9001;
            }
            if(GetUtils.getGlobalByNick(account, bot.getServerInfo().getServerName()) != null){
                return 20;
            }
                if(PermChannelUtils.getPermLevelChannel(bot.getServerInfo().getNetwork(), account, channelObject.getName()) != null) {
                    return PermChannelUtils.getPermLevelChannel(bot.getServerInfo().getNetwork(), account, channelObject.getName()).getPermLevel();
                }else{
                    return 0;
                }
            }else {
            return 0;
        }
    }
    public static int getPermLevel(PircBotX bot, User userObject, Channel channelObject){
      if(channelObject != null) {
          if (getManualPermLevel(bot, userObject.getNick(), channelObject) == -1) {
              return -1;
          } else if (getAutomaticPermLevel(bot, userObject, channelObject) < getManualPermLevel(bot, userObject.getNick(), channelObject)) {
              return getManualPermLevel(bot, userObject.getNick(), channelObject);
          } else {
              return getAutomaticPermLevel(bot, userObject, channelObject);
          }
      }else{
          String account = getAccount(bot, userObject.getNick());
          if(account != null) {
              if(GetUtils.getControllerByNick(account) != null){
                  return 9001;
              }else if(GetUtils.getGlobalByNick(account, bot.getServerInfo().getServerName()) != null){
                  return 20;
              }else{
                  return 2;
              }
          } else{
                  return 2;
              }
      }
    }
}
