package com.techcavern.wavetact.commands.chanop;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.objects.Command;
import com.techcavern.wavetact.thread.BanTime;
import com.techcavern.wavetact.utils.IRCUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.concurrent.TimeUnit;

public class Ban extends Command {
    @CMD
    public Ban() {
        super("Ban", 10, "Ban (-)[User] (time)");
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {

        if (args.length == 2 && !args[0].startsWith("-")) {
            BanTime time = new BanTime();
            time.run(args[1], IRCUtils.getUserByNick(event.getChannel(), args[0]), event.getChannel(), event.getBot());

        } else if (args.length < 2 && !args[0].startsWith("-")) {
            ban(IRCUtils.getUserByNick(event.getChannel(), args[0]), event.getChannel(), event.getBot());
        } else if (args[0].startsWith("-")) {
            unban(IRCUtils.getUserByNick(event.getChannel(), args[0].replaceFirst("-", "")), event.getChannel(), event.getBot());

        }
    }

    void ban(User u, Channel c, PircBotX b) {

        IRCUtils.setMode(c, b, "+b ", u);

    }

    void unban(User u, Channel c, PircBotX b) {
        IRCUtils.setMode(c, b, "-b ", u);
    }
}
