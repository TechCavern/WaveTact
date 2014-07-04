package com.techcavern.wavetact.commands.chanop;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.utils.objects.Command;
import com.techcavern.wavetact.utils.objects.objectUtils.QuietTimeUtils;
import com.techcavern.wavetact.utils.objects.UTime;
import com.techcavern.wavetact.utils.*;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;

public class Quiet extends Command {
    @CMD
    public Quiet() {
        super(GeneralUtils.toArray("quiet mute m"), 9, "Quiet [ircd] (-)[User] (time)");
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args)
            throws Exception {
        String hostmask;
        if (args[1].contains("!") && args[0].contains("@")) {
            hostmask = args[1];
        } else {
            if (args[1].startsWith("+"))
                hostmask = GetUtils.getUserByNick(event.getChannel(), args[1].replaceFirst("\\+", "")).getHostmask();
            else if (args[1].startsWith("-"))
                hostmask = GetUtils.getUserByNick(event.getChannel(), args[1].replaceFirst("-", "")).getHostmask();
            else
                hostmask = GetUtils.getUserByNick(event.getChannel(), args[1]).getHostmask();

        }
        if ((!args[1].startsWith("-")) && (!args[1].startsWith("\\+"))) {
            if (GetUtils.getQuietTime(hostmask) == null) {

                if (args.length == 3) {
                    quiet(hostmask, args[0],
                            event.getChannel(), event.getBot());
                    UTime c = new UTime(hostmask, event.getBot().getServerInfo().getNetwork(), args[0], event.getChannel().getName(), GeneralUtils.getMilliSeconds(args[2]) + System.currentTimeMillis());
                    GeneralRegistry.QuietTimes.add(c);
                    QuietTimeUtils.saveQuietTimes();

                } else if (args.length < 3) {
                    quiet(hostmask, args[0], event.getChannel(), event.getBot());
                    UTime c = new UTime(hostmask, event.getBot().getServerInfo().getNetwork(), args[0], event.getChannel().getName(), GeneralUtils.getMilliSeconds("7w") + System.currentTimeMillis());
                    GeneralRegistry.QuietTimes.add(c);
                    QuietTimeUtils.saveQuietTimes();

                }
            } else {
                event.getChannel().send().message("Quiet already exists!");
            }
        } else  if (args[1].startsWith("-")) {
            if(GetUtils.getQuietTime(hostmask) != null) {
                GetUtils.getQuietTime(hostmask).setTime(0);
                QuietTimeUtils.saveQuietTimes();
            }else{
                switch (args[0].toLowerCase()) {
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
            if (GetUtils.getQuietTime(hostmask) != null) {
                if (args[1].startsWith("\\+")) {
                    if (args[1].startsWith("\\+")) {
                        GetUtils.getQuietTime(hostmask).setTime(GetUtils.getQuietTime(hostmask).getTime() + GeneralUtils.getMilliSeconds(args[2].replace("\\+", "")));
                    } else if (args[1].startsWith("-")) {
                        GetUtils.getQuietTime(hostmask).setTime(GetUtils.getQuietTime(hostmask).getTime() - GeneralUtils.getMilliSeconds(args[2].replace("-", "")));

                    } else {
                        GetUtils.getQuietTime(hostmask).setTime(GeneralUtils.getMilliSeconds(args[2].replace("-", "")));
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
