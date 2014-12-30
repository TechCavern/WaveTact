package com.techcavern.wavetact.commands.chanhalfop;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.ChanHOPCMD;
import com.techcavern.wavetact.utils.*;
import com.techcavern.wavetact.utils.databaseUtils.BanTimeUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import com.techcavern.wavetact.utils.objects.TimedBan;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@CMD
@ChanHOPCMD
public class Ban extends GenericCommand {

    public Ban() {
        super(GeneralUtils.toArray("ban b"), 7, "ban (-)(+)[user][hostmask] (-)(+)(time)", "Bans a user for a specified period of time or 24 hours", true);
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
        String networkname = GetUtils.getNetworkNameByNetwork(network);
        TimedBan BanTime = BanTimeUtils.getBanTime(hostmask, networkname, channel.getName());
        if (args[0].startsWith("-")) {
            if (BanTime != null) {
                BanTime.setTime(0);
                BanTimeUtils.saveBanTimes();
            } else {
                IRCUtils.setMode(channel, network, "-b ", hostmask);
            }

        } else if (args[0].startsWith("+")) {
            if (BanTime != null) {
                if (args[0].startsWith("+")) {
                    if (args[1].startsWith("+")) {
                        BanTime.setTime(BanTime.getTime() + GeneralUtils.getMilliSeconds(args[1].replace("+", "")));
                    } else if (args[1].startsWith("-")) {
                        BanTime.setTime(BanTime.getTime() - GeneralUtils.getMilliSeconds(args[1].replace("-", "")));
                    } else {
                        BanTime.setTime(GeneralUtils.getMilliSeconds(args[1].replace("-", "")));
                    }
                    IRCUtils.sendMessage(user, network, channel, "Ban modified", prefix);
                    BanTimeUtils.saveBanTimes();
                }
            } else {
                ErrorUtils.sendError(user, "Ban does not exist!");
            }
        } else {
            if (BanTime == null) {
                if (args.length == 2) {
                    ban(hostmask, channel, network);
                    TimedBan utimeObject = new TimedBan(hostmask, networkname, "b", channel.getName(), GeneralUtils.getMilliSeconds(args[1]), System.currentTimeMillis());
                    Registry.BanTimes.add(utimeObject);
                    BanTimeUtils.saveBanTimes();

                } else if (args.length < 2) {
                    ban(hostmask, channel, network);
                    TimedBan utimeObject = new TimedBan(hostmask, networkname, "b", channel.getName(), GeneralUtils.getMilliSeconds("24h"), System.currentTimeMillis());
                    Registry.BanTimes.add(utimeObject);
                    BanTimeUtils.saveBanTimes();
                }
            } else {
                ErrorUtils.sendError(user, "Ban already exists!");
            }
        }
    }

    void ban(String hostmask, Channel channel, PircBotX networkObject) {
        IRCUtils.setMode(channel, networkObject, "+b ", hostmask);

    }
}
