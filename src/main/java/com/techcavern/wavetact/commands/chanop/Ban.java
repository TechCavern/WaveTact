package com.techcavern.wavetact.commands.chanop;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.utils.objects.Command;
import com.techcavern.wavetact.utils.objects.objectUtils.BanTimeUtils;
import com.techcavern.wavetact.utils.objects.UTime;
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
        String hostmask;
        if (args[0].contains("!") && args[0].contains("@")) {
            hostmask = args[0];
        } else {
            if (args[0].startsWith("+"))
                hostmask = GetUtils.getUserByNick(event.getChannel(), args[0].replaceFirst("\\+", "")).getHostmask();
            else if (args[0].startsWith("-"))
                hostmask = GetUtils.getUserByNick(event.getChannel(), args[0].replaceFirst("-", "")).getHostmask();
            else
                hostmask = GetUtils.getUserByNick(event.getChannel(), args[0]).getHostmask();
        }
        if ((!args[0].startsWith("-")) && (!args[0].startsWith("\\+"))) {

            if (GetUtils.getBanTime(hostmask) == null) {

                if (args.length == 2) {
                    ban(hostmask, event.getChannel(), event.getBot());
                    UTime utimeObject = new UTime(hostmask, event.getBot().getServerInfo().getNetwork(), "b", event.getChannel().getName(), GeneralUtils.getMilliSeconds(args[1]) + System.currentTimeMillis());
                    GeneralRegistry.BanTimes.add(utimeObject);
                    BanTimeUtils.saveBanTimes();

                } else if (args.length < 2) {
                    ban(hostmask, event.getChannel(), event.getBot());
                    UTime utimeObject = new UTime(hostmask, event.getBot().getServerInfo().getNetwork(), "b", event.getChannel().getName(), GeneralUtils.getMilliSeconds("7w") + System.currentTimeMillis());
                    GeneralRegistry.BanTimes.add(utimeObject);
                    BanTimeUtils.saveBanTimes();
                }
            } else {
                event.getChannel().send().message("Ban already exists!");
            }
        } else {
            if (GetUtils.getBanTime(hostmask) != null) {
                if (args[0].startsWith("-")) {
                    if(GetUtils.getBanTime(hostmask) != null) {
                        GetUtils.getBanTime(hostmask).setTime(0);
                        BanTimeUtils.saveBanTimes();
                    }else{
                        IRCUtils.setMode(event.getChannel(), event.getBot(), "-b ", hostmask);
                    }

                } else if (args[0].startsWith("\\+")) {
                    if (args[1].startsWith("\\+")) {
                        GetUtils.getBanTime(hostmask).setTime(GetUtils.getBanTime(hostmask).getTime() + GeneralUtils.getMilliSeconds(args[1].replace("\\+", "")));
                    } else if (args[1].startsWith("-")) {
                        GetUtils.getBanTime(hostmask).setTime(GetUtils.getBanTime(hostmask).getTime() - GeneralUtils.getMilliSeconds(args[1].replace("-", "")));
                    } else {
                        GetUtils.getBanTime(hostmask).setTime(GeneralUtils.getMilliSeconds(args[1].replace("-", "")));
                    }
                    event.getChannel().send().message("Ban Modified");
                    BanTimeUtils.saveBanTimes();
                } else {
                    event.getChannel().send().message("Ban does not exist!");
                }
            }
        }
    }

    void ban(String hostmask, Channel channel, PircBotX botObject) {
        IRCUtils.setMode(channel, botObject, "+b ", hostmask);

    }
}
