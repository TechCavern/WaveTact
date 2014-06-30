package com.techcavern.wavetact.thread;

import com.techcavern.wavetact.utils.IRCUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.concurrent.TimeUnit;

public class QuietTime{
    public void run(String i, String s, User u, Channel c, PircBotX b)
            throws InterruptedException {
        quiet(u, s, c, b);

        if (i.endsWith("s")) {
            int e = Integer.parseInt(i.replace("s", ""));
            TimeUnit.SECONDS.sleep(e);
        } else if (i.endsWith("m")) {
            int e = Integer.parseInt(i.replace("m", ""));
            TimeUnit.MINUTES.sleep(e);
        } else if (i.endsWith("h")) {
            int e = Integer.parseInt(i.replace("h", ""));
            TimeUnit.HOURS.sleep(e);
        } else if (i.endsWith("d")) {
            int e = Integer.parseInt(i.replace("d", ""));
            TimeUnit.DAYS.sleep(e);
        }

        unquiet(u, s, c, b);
    }

    void quiet(User u, String i, Channel c, PircBotX b) {
        if (i.equalsIgnoreCase("c")) {
            IRCUtils.setMode(c, b, "+q ", u);
        } else if (i.equalsIgnoreCase("u")) {
            IRCUtils.setMode(c, b, "+b ~q:", u);
        } else if (i.equalsIgnoreCase("i")) {
            IRCUtils.setMode(c, b, "+b m:", u);
        }
    }

    void unquiet(User u, String i, Channel c, PircBotX b) {
        if (i.equalsIgnoreCase("c")) {
            IRCUtils.setMode(c, b, "-q ", u);
        } else if (i.equalsIgnoreCase("u")) {
            IRCUtils.setMode(c, b, "-b ~q:", u);
        } else if (i.equalsIgnoreCase("i")) {
            IRCUtils.setMode(c, b, "-b m:", u);
        }
    }
}