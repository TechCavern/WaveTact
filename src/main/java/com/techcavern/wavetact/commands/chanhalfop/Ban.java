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

@CMD
@ChanHOPCMD
public class Ban extends GenericCommand {

    public Ban() {
        super(GeneralUtils.toArray("ban b"), 7, "ban (-)(+)[User][hostmask] (-)(+)(time)", "bans a user for a specified period of time or 24 hours", true);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {

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
                hostmask = IRCUtils.getHostmask(network, args[0].replaceFirst("\\+", ""), true);
            } else if (args[0].startsWith("-")) {
                hostmask = IRCUtils.getHostmask(network, args[0].replaceFirst("-", ""), true);

            } else {
                hostmask = IRCUtils.getHostmask(network, args[0], true);

            }
        }

        if (args[0].startsWith("-")) {
            if (BanTimeUtils.getBanTime(hostmask) != null) {
                BanTimeUtils.getBanTime(hostmask).setTime(0);
                BanTimeUtils.saveBanTimes();
            } else {
                IRCUtils.setMode(channel, network, "-b ", hostmask);
            }

        } else if (args[0].startsWith("+")) {
            if (BanTimeUtils.getBanTime(hostmask) != null) {
                if (args[0].startsWith("+")) {
                    if (args[1].startsWith("+")) {
                        BanTimeUtils.getBanTime(hostmask).setTime(BanTimeUtils.getBanTime(hostmask).getTime() + GeneralUtils.getMilliSeconds(args[1].replace("+", "")));
                    } else if (args[1].startsWith("-")) {
                        BanTimeUtils.getBanTime(hostmask).setTime(BanTimeUtils.getBanTime(hostmask).getTime() - GeneralUtils.getMilliSeconds(args[1].replace("-", "")));
                    } else {
                        BanTimeUtils.getBanTime(hostmask).setTime(GeneralUtils.getMilliSeconds(args[1].replace("-", "")));
                    }
                    IRCUtils.sendAction(user, network, channel, "Ban Modified", prefix);
                    BanTimeUtils.saveBanTimes();
                }
            } else {
                IRCUtils.sendError(user, "Ban does not exist!");
            }
        } else {
            if (BanTimeUtils.getBanTime(hostmask) == null) {

                if (args.length == 2) {
                    ban(hostmask, channel, network);
                    UTime utimeObject = new UTime(hostmask, network.getServerInfo().getNetwork(), "b", channel.getName(), GeneralUtils.getMilliSeconds(args[1]), System.currentTimeMillis());
                    GeneralRegistry.BanTimes.add(utimeObject);
                    BanTimeUtils.saveBanTimes();

                } else if (args.length < 2) {
                    ban(hostmask, channel, network);
                    UTime utimeObject = new UTime(hostmask, network.getServerInfo().getNetwork(), "b", channel.getName(), GeneralUtils.getMilliSeconds("24h"), System.currentTimeMillis());
                    GeneralRegistry.BanTimes.add(utimeObject);
                    BanTimeUtils.saveBanTimes();
                }
            } else {
                IRCUtils.sendError(user, "Ban already exists!");
            }
        }
    }

    void ban(String hostmask, Channel channel, PircBotX networkObject) {
        IRCUtils.setMode(channel, networkObject, "+b ", hostmask);

    }
}
