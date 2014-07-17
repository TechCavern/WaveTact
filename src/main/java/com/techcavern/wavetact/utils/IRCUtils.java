package com.techcavern.wavetact.utils;

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

    public static void SendNotice(PircBotX botObject, User userObject, String notice) {
        OutputUser x = new OutputUser(botObject, userObject);
        x.notice(notice);
    }
    public static void SendMessage(User userObject, Channel channelObject, String message,boolean isPrivate ){
        if(channelObject != null && !isPrivate){
            channelObject.send().message(message);
        }else{
            userObject.send().message(message);
        }
    }
    public static void SendAction(User userObject, Channel channelObject, String message, boolean isPrivate){
        if(channelObject != null && !isPrivate){
            channelObject.send().action(message);
        }else{
            userObject.send().action(message);
        }
    }
    public static String getBanmask(PircBotX bot, String userObject) {
        String banmask;
        if(userObject != null) {
            bot.sendRaw().rawLineNow("WHOIS " + userObject);
        }else{
            banmask = null;
        }
        WaitForQueue waitForQueue = new WaitForQueue(bot);
        WhoisEvent<PircBotX> test;
        try {
            test = waitForQueue.waitFor(WhoisEvent.class);
            waitForQueue.close();
            String hostname = test.getHostname();
            String Login = test.getLogin();
            if(!Login.startsWith("~")){
                banmask = "*!" + Login+"@"+hostname;
            }else{
                banmask = "*!*@"+hostname;
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
            banmask = null;
        }
        return banmask.replace(" ", "");
    }
    public static String getHostmask(PircBotX bot, String userObject) {
        String hostmask;
        if(userObject != null) {
            bot.sendRaw().rawLineNow("WHOIS " + userObject);
        }else{
            hostmask = null;
        }
        WaitForQueue waitForQueue = new WaitForQueue(bot);
        WhoisEvent<PircBotX> test;
        try {
            test = waitForQueue.waitFor(WhoisEvent.class);
            waitForQueue.close();
            String hostname = test.getHostname();
            String Login = test.getLogin();
        //    String Nick = test.getNick();
            hostmask = userObject +"!" + Login+"@"+hostname;
        } catch (InterruptedException ex) {
            ex.printStackTrace();
            hostmask = null;
        }

        return hostmask.replace(" ", "");
    }
}
