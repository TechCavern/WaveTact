package com.techcavern.wavetact.commands.chanop;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.objects.Command;
import com.techcavern.wavetact.objects.UTime;
import com.techcavern.wavetact.utils.*;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
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
            if(args[0].startsWith("+"))
                h= GetUtils.getUserByNick(event.getChannel(), args[0].replaceFirst("\\+", "")).getHostmask();
            else if(args[0].startsWith("-"))
                h= GetUtils.getUserByNick(event.getChannel(), args[0].replaceFirst("-", "")).getHostmask();
            else
                h= GetUtils.getUserByNick(event.getChannel(), args[0]).getHostmask();
        }
        if ((!args[0].startsWith("-")) && (!args[0].startsWith("+"))) {

            if (GetUtils.getBanTime(h) == null) {

                if (args.length == 2) {
                    ban(h,event.getChannel(), event.getBot());
                    UTime c = new UTime(h, event.getBot().getServerInfo().getNetwork(),"b", event.getChannel().getName(), GeneralUtils.getMilliSeconds(args[1]) + System.currentTimeMillis());
                    GeneralRegistry.BanTimes.add(c);
                    SaveUtils.saveBanTimes();

                } else if (args.length < 2) {
                    ban(h, event.getChannel(), event.getBot());
                    UTime c = new UTime(h, event.getBot().getServerInfo().getNetwork(), "b", event.getChannel().getName(), GeneralUtils.getMilliSeconds("7w") + System.currentTimeMillis());
                    GeneralRegistry.BanTimes.add(c);
                    SaveUtils.saveBanTimes();
                }
            } else {
                event.getChannel().send().message("Ban already exists!");
            }
        } else {
            if (GetUtils.getBanTime(h) != null) {
                if (args[0].startsWith("-")) {
                    GetUtils.getBanTime(h).setTime(0);
                    SaveUtils.saveBanTimes();

                } else if (args[0].startsWith("+")) {
                    if(args[1].startsWith("+")) {
                        GetUtils.getBanTime(h).setTime(GetUtils.getBanTime(h).getTime() + GeneralUtils.getMilliSeconds(args[1].replace("+", "")));
                        }else
                        if(args[1].startsWith("-")) {
                            GetUtils.getBanTime(h).setTime(GetUtils.getBanTime(h).getTime() - GeneralUtils.getMilliSeconds(args[1].replace("-", "")));
                        }else{
                            GetUtils.getBanTime(h).setTime(GeneralUtils.getMilliSeconds(args[1].replace("-", "")));
                        }
                        event.getChannel().send().message("Ban Modified");
                        SaveUtils.saveBanTimes();
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
