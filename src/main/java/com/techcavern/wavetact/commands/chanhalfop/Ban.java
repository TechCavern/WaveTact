package com.techcavern.wavetact.commands.chanhalfop;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.ChanHOPCMD;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.databaseUtils.BanTimeUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import com.techcavern.wavetact.utils.objects.UTime;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

public class Ban extends GenericCommand {
    @CMD
    @ChanHOPCMD
    public Ban() {
        super(GeneralUtils.toArray("ban b"), 6, "Ban (-)(+)[User] (time)", "bans a user for a specified period of time or 24 hours");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {

        String hostmask;
        if (args[0].contains("!") && args[0].contains("@")) {
            if (args[0].startsWith("-")) {
                hostmask = args[0].replaceFirst("-", "");
            } else if (args[0].startsWith("+")) {
                hostmask = args[0].replaceFirst("\\+", "");
            } else {
                hostmask = args[0];
            }
        } else {
            if (args[0].startsWith("+")) {
                hostmask = IRCUtils.getHostmask(Bot, args[0].replaceFirst("\\+", ""), true);
            } else if (args[0].startsWith("-")) {
                hostmask = IRCUtils.getHostmask(Bot, args[0].replaceFirst("-", ""), true);

            } else {
                hostmask = IRCUtils.getHostmask(Bot, args[0], true);

            }
        }
        if ((!args[0].startsWith("-")) && (!args[0].startsWith("+"))) {

            if (BanTimeUtils.getBanTime(hostmask) == null) {

                if (args.length == 2) {
                    ban(hostmask, channel, Bot);
                    UTime utimeObject = new UTime(hostmask, Bot.getServerInfo().getNetwork(), "b", channel.getName(), GeneralUtils.getMilliSeconds(args[1]), System.currentTimeMillis());
                    GeneralRegistry.BanTimes.add(utimeObject);
                    BanTimeUtils.saveBanTimes();

                } else if (args.length < 2) {
                    ban(hostmask, channel, Bot);
                    UTime utimeObject = new UTime(hostmask, Bot.getServerInfo().getNetwork(), "b", channel.getName(), GeneralUtils.getMilliSeconds("24h"), System.currentTimeMillis());
                    GeneralRegistry.BanTimes.add(utimeObject);
                    BanTimeUtils.saveBanTimes();
                }
            } else {
                IRCUtils.sendError(user, "Ban already exists!");
            }
        } else if (args[0].startsWith("-")) {
            if (BanTimeUtils.getBanTime(hostmask) != null) {
                BanTimeUtils.getBanTime(hostmask).setTime(0);
                BanTimeUtils.saveBanTimes();
            } else {
                IRCUtils.setMode(channel, Bot, "-b ", hostmask);
            }

        } else {
            if (BanTimeUtils.getBanTime(hostmask) != null) {
                if (args[0].startsWith("+")) {
                    if (args[1].startsWith("+")) {
                        BanTimeUtils.getBanTime(hostmask).setTime(BanTimeUtils.getBanTime(hostmask).getTime() + GeneralUtils.getMilliSeconds(args[1].replace("+", "")));
                    } else if (args[1].startsWith("-")) {
                        BanTimeUtils.getBanTime(hostmask).setTime(BanTimeUtils.getBanTime(hostmask).getTime() - GeneralUtils.getMilliSeconds(args[1].replace("-", "")));
                    } else {
                        BanTimeUtils.getBanTime(hostmask).setTime(GeneralUtils.getMilliSeconds(args[1].replace("-", "")));
                    }
                    channel.send().message("Ban Modified");
                    BanTimeUtils.saveBanTimes();
                }
            } else {
                channel.send().message("Ban does not exist!");
            }
        }
    }

    void ban(String hostmask, Channel channel, PircBotX botObject) {
        IRCUtils.setMode(channel, botObject, "+b ", hostmask);

    }
}
