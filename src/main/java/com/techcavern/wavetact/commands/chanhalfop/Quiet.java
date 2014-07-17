package com.techcavern.wavetact.commands.chanhalfop;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import com.techcavern.wavetact.utils.databaseUtils.QuietTimeUtils;
import com.techcavern.wavetact.utils.objects.UTime;
import com.techcavern.wavetact.utils.*;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;

public class Quiet extends GenericCommand {
    @CMD
    public Quiet() {
        super(GeneralUtils.toArray("quiet mute m"), 6, "Quiet (-)[User][hostmask] (time)");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel,boolean isPrivate, String... args)
            throws Exception {
        String ircd;
        if(Bot.getServerInfo().getChannelModes().contains("q")){
            ircd = "c";
        }else if (Bot.getServerInfo().getServerVersion().contains("InspIRCd")){
            ircd = "i";
        }else if (Bot.getServerInfo().getServerVersion().contains("Unreal")){
            ircd = "u";
        }else{
            ircd = null;
        }
        String hostmask;
        if (args[0].contains("!") && args[0].contains("@")) {
            if(args[0].startsWith("-")) {
                hostmask = args[0].replaceFirst("-", "");
            }else if(args[0].startsWith("+")){
                hostmask = args[0].replaceFirst("\\+", "");
            }else{
                hostmask = args[0];
            }
        } else {
            if (args[0].startsWith("+")) {
                hostmask = IRCUtils.getBanmask(Bot, args[0].replaceFirst("\\+", ""));
            } else if (args[0].startsWith("-")) {
                hostmask = IRCUtils.getBanmask(Bot, args[0].replaceFirst("-", ""));
            } else {
                hostmask = IRCUtils.getBanmask(Bot, args[0]);

            }

        }
        if ((!args[0].startsWith("-")) && (!args[0].startsWith("+"))) {
            if (QuietTimeUtils.getQuietTime(hostmask) == null) {

                if (args.length == 2) {
                    quiet(hostmask, ircd,
                            channel, Bot);
                    UTime c = new UTime(hostmask, Bot.getServerInfo().getNetwork(), ircd, channel.getName(), GeneralUtils.getMilliSeconds(args[1]), System.currentTimeMillis());
                    GeneralRegistry.QuietTimes.add(c);
                    QuietTimeUtils.saveQuietTimes();

                } else if (args.length < 2) {
                    quiet(hostmask, ircd, channel, Bot);
                    UTime c = new UTime(hostmask, Bot.getServerInfo().getNetwork(), ircd, channel.getName(), GeneralUtils.getMilliSeconds("24h"), System.currentTimeMillis());
                    GeneralRegistry.QuietTimes.add(c);
                    QuietTimeUtils.saveQuietTimes();

                }
            } else {
                channel.send().message("Quiet already exists!");
            }
        } else  if (args[0].startsWith("-")) {
            if(QuietTimeUtils.getQuietTime(hostmask) != null) {
                QuietTimeUtils.getQuietTime(hostmask).setTime(0);
                QuietTimeUtils.saveQuietTimes();
            }else{
                switch (ircd.toLowerCase()) {
                    case "c":
                        IRCUtils.setMode(channel, Bot, "-q ", hostmask);
                        break;
                    case "u":
                        IRCUtils.setMode(channel, Bot, "-b ~q:",hostmask);
                        break;
                    case "i":
                        IRCUtils.setMode(channel, Bot, "-b m:",hostmask);
                        break;
                }
            }
        } else {
            if (QuietTimeUtils.getQuietTime(hostmask) != null) {
                if (args[0].startsWith("+")) {
                    if (args[1].startsWith("+")) {
                        QuietTimeUtils.getQuietTime(hostmask).setTime(QuietTimeUtils.getQuietTime(hostmask).getTime() + GeneralUtils.getMilliSeconds(args[1].replace("+", "")));
                    } else if (args[1].startsWith("-")) {
                        QuietTimeUtils.getQuietTime(hostmask).setTime(QuietTimeUtils.getQuietTime(hostmask).getTime() - GeneralUtils.getMilliSeconds(args[1].replace("-", "")));

                    } else {
                        QuietTimeUtils.getQuietTime(hostmask).setTime(GeneralUtils.getMilliSeconds(args[1].replace("-", "")));
                    }
                    channel.send().message("Quiet Modified");
                    QuietTimeUtils.saveQuietTimes();
                }} else {
                    channel.send().message("Quiet does not exist!");
                }
            }
        }


    void quiet(String hostmask, String type, Channel channelName, PircBotX botObject) {
        switch (type.toLowerCase()) {
            case "c":
                IRCUtils.setMode(channelName, botObject, "+q ", hostmask);
                break;
            case "u":
                IRCUtils.setMode(channelName, botObject, "+b ~q:", hostmask);
                break;
            case "i":
                IRCUtils.setMode(channelName, botObject, "+b m:", hostmask);
                break;
        }
    }
}
