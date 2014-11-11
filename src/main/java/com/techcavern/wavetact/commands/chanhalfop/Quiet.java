package com.techcavern.wavetact.commands.chanhalfop;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.ChanHOPCMD;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.database.QuietTimeUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import com.techcavern.wavetact.utils.objects.UTime;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@CMD
@ChanHOPCMD
public class Quiet extends GenericCommand {

    public Quiet() {
        super(GeneralUtils.toArray("quiet mute"), 6, "Quiet (-)[User][hostmask] (-)(+)(time)", "quiets a user for the specified time or 24 hours");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {

        String ircd;
        if (Bot.getServerInfo().getChannelModes().contains("q")) {
            ircd = "c";
        } else if (Bot.getServerInfo().getExtBanPrefix().equalsIgnoreCase("~") && Bot.getServerInfo().getExtBanList() != null && Bot.getServerInfo().getExtBanList().contains("q")) {
            ircd = "u";
        } else if (Bot.getServerInfo().getExtBanPrefix().contains("m") && Bot.getServerInfo().getExtBanList() == null) {
            ircd = "i";
        } else {
            IRCUtils.sendError(user, "This networks ircd is not supported for quiets.");
            return;
        }
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
        if (args[0].startsWith("+")) {
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
                }
            } else {
                channel.send().message("Quiet does not exist!");
            }
        } else if (args[0].startsWith("-")) {
            if (QuietTimeUtils.getQuietTime(hostmask) != null) {
                QuietTimeUtils.getQuietTime(hostmask).setTime(0);
                QuietTimeUtils.saveQuietTimes();
            } else {
                IRCUtils.setMode(channel, Bot, "-"+GeneralRegistry.QuietBans.get(ircd), hostmask);
            }
        } else {

            if (QuietTimeUtils.getQuietTime(hostmask) == null) {
                if (args.length == 2) {
                    IRCUtils.setMode(channel, Bot, "+" +GeneralRegistry.QuietBans.get(ircd), hostmask);
                    UTime c = new UTime(hostmask, Bot.getServerInfo().getNetwork(), ircd, channel.getName(), GeneralUtils.getMilliSeconds(args[1]), System.currentTimeMillis());
                    GeneralRegistry.QuietTimes.add(c);
                    QuietTimeUtils.saveQuietTimes();

                } else if (args.length < 2) {
                    IRCUtils.setMode(channel, Bot, "+" +GeneralRegistry.QuietBans.get(ircd), hostmask);
                    UTime c = new UTime(hostmask, Bot.getServerInfo().getNetwork(), ircd, channel.getName(), GeneralUtils.getMilliSeconds("24h"), System.currentTimeMillis());
                    GeneralRegistry.QuietTimes.add(c);
                    QuietTimeUtils.saveQuietTimes();

                }
            } else {
                channel.send().message("Quiet already exists!");
            }
        }
    }



}
