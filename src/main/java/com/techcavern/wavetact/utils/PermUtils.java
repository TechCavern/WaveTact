package com.techcavern.wavetact.utils;

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

    private static boolean isController(PircBotX bot, User userObject) {
        String v = getAccount(bot, userObject);
        if (v != null) {
            return GeneralRegistry.Controllers.contains(v.toLowerCase());
        } else {
            return GeneralRegistry.ControllerHostmasks.contains(userObject.getHostmask());
        }
    }

    public static int getPermLevel(PircBotX bot, User userObject, Channel channelObject) {
        if (PermUtils.isController(bot, userObject)) {
            return 9001;
        } else if (channelObject.isOwner(userObject)) {
            return 15;
        } else if (channelObject.isOp(userObject) || channelObject.isSuperOp(userObject)) {
            return 10;
        } else if (channelObject.isHalfOp(userObject) || channelObject.hasVoice(userObject)) {
            return 5;
        } else {
            return 0;
        }
    }
}
