package com.techcavern.wavetact.commands.chanop;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.objects.Command;
import com.techcavern.wavetact.objects.UTime;
import com.techcavern.wavetact.thread.CheckTime;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;

public class Ban extends Command {
    @CMD
    public Ban() {
        super("Ban", 10, "Ban (-)(+)[User] (time)");
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args)
            throws Exception {
        if ((!args[0].startsWith("-")) && (!args[0].startsWith("+"))) {
            if (IRCUtils.getBanTime(IRCUtils.getUserByNick(event.getChannel(), args[0]).getHostmask()) == null) {

                if (args.length == 2) {
                    ban(IRCUtils.getUserByNick(event.getChannel(), args[0]),
                            event.getChannel(), event.getBot());
                    UTime c = new UTime(IRCUtils.getUserByNick(event.getChannel(), args[0]).getHostmask(), event.getBot().getServerInfo().getNetwork(),"b", event.getChannel().getName(), GeneralUtils.getMilliSeconds(args[1]) + System.currentTimeMillis());
                    GeneralRegistry.BanTimes.add(c);

                } else if (args.length < 2) {
                    ban(IRCUtils.getUserByNick(event.getChannel(), args[1]), event.getChannel(), event.getBot());
                    UTime c = new UTime(IRCUtils.getUserByNick(event.getChannel(), args[0]).getHostmask(), event.getBot().getServerInfo().getNetwork(), "b", event.getChannel().getName(), GeneralUtils.getMilliSeconds("7w") + System.currentTimeMillis());
                    GeneralRegistry.BanTimes.add(c);
                }
            } else {
                event.getChannel().send().message("Ban already exists!");
            }
        } else {
            if (IRCUtils.getBanTime(IRCUtils.getUserByNick(event.getChannel(), args[0].replace("-", "").replace("+", "")).getHostmask()) != null) {
                if (args[0].startsWith("-")) {
                    IRCUtils.getBanTime(IRCUtils.getUserByNick(event.getChannel(), args[0].replace("-", "")).getHostmask()).setTime(0);
                } else if (args[0].startsWith("+")) {
                    IRCUtils.getBanTime(IRCUtils.getUserByNick(event.getChannel(), args[0].replace("+", "")).getHostmask()).setTime(IRCUtils.getBanTime(IRCUtils.getUserByNick(event.getChannel(), args[0].replace("+", "")).getHostmask()).getTime() + GeneralUtils.getMilliSeconds(args[1]));

                } else {
                    event.getChannel().send().message("Ban does not exist!");
                }
            }
        }
    }

    void ban(User u, Channel c, PircBotX b) {
                IRCUtils.setMode(c, b, "+b ", u.getHostmask());

    }
}
