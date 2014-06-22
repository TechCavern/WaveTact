package com.techcavern.wavetact.commands.chanop;

import com.techcavern.wavetact.utils.Command;

import java.util.concurrent.TimeUnit;

import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;

import com.techcavern.wavetact.utils.IRCUtils;

public class Ban extends Command {

    public Ban() {
        super("Ban", 10);
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {

        if (args.length == 2 && !args[0].startsWith("-")) {
            if (args[1].endsWith("s") || args[1].endsWith("h") || args[1].endsWith("m") || args[1].endsWith("d")) {
                bantime time = new bantime();
                time.run(args[1], IRCUtils.getUserByNick(event.getChannel(), args[0]), event.getChannel(), event.getBot());
            } else {
                IRCUtils.SendNotice(event.getBot(), event.getUser(), " Ensure you have specified a valid time (30s = 30 Seconds, 30m = 30 minutes, up to days)");
            }
        } else if (args.length < 2 && !args[0].startsWith("-")) {
            ban(IRCUtils.getUserByNick(event.getChannel(), args[0]), event.getChannel(), event.getBot());
        } else if (args[0].startsWith("-")) {
            unban(IRCUtils.getUserByNick(event.getChannel(), args[0].replaceFirst("-", "")), event.getChannel(), event.getBot());

        } else {
            IRCUtils.SendNotice(event.getBot(), event.getUser(), "Syntax: @quiet [ircd code] (-)[User to Quiet] (time in seconds)");
        }

    }

    public class bantime extends Thread {

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
    }

    public void ban(User u, Channel c, PircBotX b) {

        IRCUtils.setMode(c, b, "+b ", u);

    }

    public void unban(User u, Channel c, PircBotX b) {
        IRCUtils.setMode(c, b, "-b ", u);
    }
}
