package com.techcavern.wavetact.commands.chanop;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.objects.Command;
import com.techcavern.wavetact.thread.QuietTime;
import com.techcavern.wavetact.utils.IRCUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;

public class Quiet extends Command {
    @CMD
    public Quiet() {
        super("Quiet", 10, "Quiet [ircd] (-)[User] (time)");
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args)
            throws Exception {
        if ((args.length == 4) && (!args[1].startsWith("-"))) {
            QuietTime time = new QuietTime();
            time.run(args[2], args[0],
                    IRCUtils.getUserByNick(event.getChannel(), args[1]),
                    event.getChannel(), event.getBot());

        } else if ((args.length < 4) && (!args[1].startsWith("-"))) {
            quiet(IRCUtils.getUserByNick(event.getChannel(), args[1]),
                    args[0], event.getChannel(), event.getBot());
        } else if (args[1].startsWith("-")) {
            unquiet(IRCUtils.getUserByNick(event.getChannel(),
                            args[1].replaceFirst("-", "")), args[0],
                    event.getChannel(), event.getBot());

        }
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
