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
        super(GeneralUtils.toArray("Ban b"), 9, "Ban (-)(+)[User] (time)");
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args)
            throws Exception {
        String h;
        if(args[0].contains("!") && args[0].contains("@")){
            h = args[0];
        }else{
            h=IRCUtils.getUserByNick(event.getChannel(), args[0].replace("+", "").replace("-", "")).getHostmask();
        }
        if ((!args[0].startsWith("-")) && (!args[0].startsWith("+"))) {

            if (IRCUtils.getBanTime(IRCUtils.getUserByNick(event.getChannel(), args[0]).getHostmask()) == null) {

                if (args.length == 2) {
                    ban(h,event.getChannel(), event.getBot());
                    UTime c = new UTime(h, event.getBot().getServerInfo().getNetwork(),"b", event.getChannel().getName(), GeneralUtils.getMilliSeconds(args[1]) + System.currentTimeMillis());
                    GeneralRegistry.BanTimes.add(c);

                } else if (args.length < 2) {
                    ban(h, event.getChannel(), event.getBot());
                    UTime c = new UTime(h, event.getBot().getServerInfo().getNetwork(), "b", event.getChannel().getName(), GeneralUtils.getMilliSeconds("7w") + System.currentTimeMillis());
                    GeneralRegistry.BanTimes.add(c);
                }
            } else {
                event.getChannel().send().message("Ban already exists!");
            }
        } else {
            if (IRCUtils.getBanTime(h) != null) {
                if (args[0].startsWith("-")) {
                    IRCUtils.getBanTime(h).setTime(0);
                } else if (args[0].startsWith("+")) {
                    if(args[1].startsWith("+")) {
                        IRCUtils.getBanTime(h).setTime(IRCUtils.getBanTime(h).getTime() + GeneralUtils.getMilliSeconds(args[1].replace("+", "")));
                        }else
                        if(args[1].startsWith("-")) {
                            IRCUtils.getBanTime(h).setTime(IRCUtils.getBanTime(h).getTime() + GeneralUtils.getMilliSeconds(args[1].replace("-", "")));
                        }else{
                            IRCUtils.getBanTime(h).setTime(GeneralUtils.getMilliSeconds(args[1].replace("-", "")));
                        }
                    } else {
                    event.getChannel().send().message("Ban does not exist!");
                }
            }
        }
    }

    void ban(String g, Channel c, PircBotX b) {
                IRCUtils.setMode(c, b, "+b ", g);

    }
}
