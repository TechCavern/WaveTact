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
    public static void setMode(Channel c, PircBotX b, String d, String u) {
        OutputChannel o = new OutputChannel(b, c);
        if (u != null) {
            o.setMode(d, u);
        } else {
            o.setMode(d);
        }
    }

    public static void SendNotice(PircBotX b, User u, String s) {
        OutputUser x = new OutputUser(b, u);
        x.notice(s);
    }
}
