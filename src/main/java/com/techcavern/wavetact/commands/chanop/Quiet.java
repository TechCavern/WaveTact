package com.techcavern.wavetact.commands.chanop;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.objects.Command;
import com.techcavern.wavetact.objects.UTime;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;

public class Quiet extends Command {
    @CMD
    public Quiet() {
        super(GeneralUtils.toArray("quiet mute q m"), 9, "Quiet [ircd] (-)[User] (time)");
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args)
            throws Exception {
        if ((!args[1].startsWith("-")) && (!args[1].startsWith("+"))) {
            if (IRCUtils.getQuietTime(IRCUtils.getUserByNick(event.getChannel(), args[1]).getHostmask()) == null) {

                if (args.length == 3) {
                    quiet(IRCUtils.getUserByNick(event.getChannel(), args[1]),args[0],
                            event.getChannel(), event.getBot());
                    UTime c = new UTime(IRCUtils.getUserByNick(event.getChannel(), args[1]).getHostmask(), event.getBot().getServerInfo().getNetwork(), args[0], event.getChannel().getName(), GeneralUtils.getMilliSeconds(args[2]) + System.currentTimeMillis());
                    GeneralRegistry.QuietTimes.add(c);

                } else if (args.length < 3) {
                    quiet(IRCUtils.getUserByNick(event.getChannel(), args[1]), args[0], event.getChannel(), event.getBot());
                    UTime c = new UTime(IRCUtils.getUserByNick(event.getChannel(), args[1]).getHostmask(), event.getBot().getServerInfo().getNetwork(), args[0], event.getChannel().getName(), GeneralUtils.getMilliSeconds("7w") + System.currentTimeMillis());
                    GeneralRegistry.QuietTimes.add(c);
                }
            } else {
                event.getChannel().send().message("Quiet already exists!");
            }
        } else {
            if (IRCUtils.getQuietTime(IRCUtils.getUserByNick(event.getChannel(), args[1].replace("+", "").replace("-", "")).getHostmask()) != null) {
                if (args[1].startsWith("-")) {
                    IRCUtils.getQuietTime(IRCUtils.getUserByNick(event.getChannel(), args[1].replace("-", "")).getHostmask()).setTime(0);
                } else if (args[1].startsWith("+")) {
                    IRCUtils.getQuietTime(IRCUtils.getUserByNick(event.getChannel(), args[1].replace("+", "")).getHostmask()).setTime(IRCUtils.getBanTime(IRCUtils.getUserByNick(event.getChannel(), args[1].replace("+", "")).getHostmask()).getTime() + GeneralUtils.getMilliSeconds(args[2]));

                } else {
                    event.getChannel().send().message("Quiet does not exist!");
                }
            }
        }
    }

    void quiet(User u, String i, Channel c, PircBotX b) {
        switch(i.toLowerCase()) {
            case "c":
            IRCUtils.setMode(c, b, "+q ", u.getHostmask());
                break;
            case "u":
        IRCUtils.setMode(c, b, "+b ~q:", u.getHostmask());
                break;
            case "i":
            IRCUtils.setMode(c, b, "+b m:", u.getHostmask());
                break;
        }
    }
}
