package com.techcavern.wavetact.commands.chanhalfop;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.utils.objects.Command;
import com.techcavern.wavetact.utils.objects.objectUtils.QuietTimeUtils;
import com.techcavern.wavetact.utils.objects.UTime;
import com.techcavern.wavetact.utils.*;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;

public class Quiet extends Command {
    @CMD
    public Quiet() {
        super(GeneralUtils.toArray("quiet mute m"), 6, "Quiet (-)[User][hostmask] (time)");
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args)
            throws Exception {
        String ircd;
        if(event.getBot().getServerInfo().getChannelModes().contains("q")){
            ircd = "c";
        }else if (event.getBot().getServerInfo().getExceptBans().contains("m")){
            ircd = "i";
        }else if (event.getBot().getServerInfo().getExceptBans().contains("q")){
            ircd = "u";
        }else{
            ircd = null;
        }
        String hostmask;
        if (args[0].contains("!") && args[0].contains("@")) {
            hostmask = args[0];
        } else {
            if (args[0].startsWith("+")) {
                User user = GetUtils.getUserByNick(event.getChannel(), args[0].replaceFirst("\\+", ""));
                if (user.getLogin().startsWith("~")) {
                    hostmask = "*!*@" + user.getHostmask();
                } else {
                    hostmask = "*!" + user.getLogin() + "@" + user.getHostmask();
                }
            } else if (args[0].startsWith("-")) {
                User user = GetUtils.getUserByNick(event.getChannel(), args[0].replaceFirst("-", ""));
                if (user.getLogin().startsWith("~")) {
                    hostmask = "*!*@" + user.getHostmask();
                } else {
                    hostmask = "*!" + user.getLogin() + "@" + user.getHostmask();
                }
            } else {
                User user = GetUtils.getUserByNick(event.getChannel(), args[0]);
                if (user.getLogin().startsWith("~")) {
                    hostmask = "*!*@" + user.getHostmask();
                } else {
                    hostmask = "*!" + user.getLogin() + "@" + user.getHostmask();
                }
            }

        }
        if ((!args[0].startsWith("-")) && (!args[0].startsWith("+"))) {
            if (QuietTimeUtils.getQuietTime(hostmask) == null) {

                if (args.length == 2) {
                    quiet(hostmask, ircd,
                            event.getChannel(), event.getBot());
                    UTime c = new UTime(hostmask, event.getBot().getServerInfo().getNetwork(), ircd, event.getChannel().getName(), GeneralUtils.getMilliSeconds(args[1]), System.currentTimeMillis());
                    GeneralRegistry.QuietTimes.add(c);
                    QuietTimeUtils.saveQuietTimes();

                } else if (args.length < 2) {
                    quiet(hostmask, ircd, event.getChannel(), event.getBot());
                    UTime c = new UTime(hostmask, event.getBot().getServerInfo().getNetwork(), ircd, event.getChannel().getName(), GeneralUtils.getMilliSeconds("24h"), System.currentTimeMillis());
                    GeneralRegistry.QuietTimes.add(c);
                    QuietTimeUtils.saveQuietTimes();

                }
            } else {
                event.getChannel().send().message("Quiet already exists!");
            }
        } else  if (args[0].startsWith("-")) {
            if(QuietTimeUtils.getQuietTime(hostmask) != null) {
                QuietTimeUtils.getQuietTime(hostmask).setTime(0);
                QuietTimeUtils.saveQuietTimes();
            }else{
                switch (ircd.toLowerCase()) {
                    case "c":
                        IRCUtils.setMode(event.getChannel(), event.getBot(), "-q ", hostmask);
                        break;
                    case "u":
                        IRCUtils.setMode(event.getChannel(), event.getBot(), "-b ~q:", hostmask);
                        break;
                    case "i":
                        IRCUtils.setMode(event.getChannel(), event.getBot(), "-b m:", hostmask);
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
                    event.getChannel().send().message("Quiet Modified");
                    QuietTimeUtils.saveQuietTimes();
                } else {
                    event.getChannel().send().message("Quiet does not exist!");
                }
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
