package com.techcavern.wavetact.utils;

import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.output.OutputChannel;
import org.pircbotx.output.OutputUser;

/**
 * Created by jztech101 on 7/4/14.
 */
public class IRCUtils {
    public static void setMode(Channel channelObject, PircBotX botObject, String modeToSet, String hostmask) {
        OutputChannel o = new OutputChannel(botObject, channelObject);
        if (hostmask != null) {
            o.setMode(modeToSet, hostmask);
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
}
