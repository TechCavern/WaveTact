package com.techcavern.wavetact.thread;

import com.techcavern.wavetact.utils.IRCUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.concurrent.TimeUnit;

public class BanTime {
    public void run(String i, User u, Channel c, PircBotX b) throws InterruptedException {
        ban(u, c, b);
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

        unban(u, c, b);
    }

    void ban(User u, Channel c, PircBotX b) {

        IRCUtils.setMode(c, b, "+b ", u);

    }

    void unban(User u, Channel c, PircBotX b) {
        IRCUtils.setMode(c, b, "-b ", u);
    }
}