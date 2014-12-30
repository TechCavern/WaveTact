package com.techcavern.wavetact.commands.chanhalfop;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.ChanHOPCMD;
import com.techcavern.wavetact.utils.*;
import com.techcavern.wavetact.utils.databaseUtils.QuietTimeUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import com.techcavern.wavetact.utils.objects.TimedBan;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@CMD
@ChanHOPCMD
public class Quiet extends GenericCommand {

    public Quiet() {
        super(GeneralUtils.toArray("quiet mute"), 7, "quiet (-)[user][hostmask] (-)(+)(time)", "Quiets a user for the specified time or 24 hours", true);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {

        String ircd;
        if (network.getServerInfo().getChannelModes().contains("q")) {
            ircd = "c";
        } else if (network.getServerInfo().getExtBanPrefix() != null && network.getServerInfo().getExtBanPrefix().equalsIgnoreCase("~") && network.getServerInfo().getExtBanList() != null && network.getServerInfo().getExtBanList().contains("q")) {
            ircd = "u";
        } else if (network.getServerInfo().getExtBanList().contains("m") && network.getServerInfo().getExtBanPrefix() == null) {
            ircd = "i";
        } else {
            ErrorUtils.sendError(user, "This networks ircd is not supported for quiets.");
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
                hostmask = IRCUtils.getHostmask(network, args[0].replaceFirst("\\+", ""), true);
            } else if (args[0].startsWith("-")) {
                hostmask = IRCUtils.getHostmask(network, args[0].replaceFirst("-", ""), true);
            } else {
                hostmask = IRCUtils.getHostmask(network, args[0], true);

            }

        }
        String networkname = GetUtils.getNetworkNameByNetwork(network);
        TimedBan QuietTime = QuietTimeUtils.getQuietTime(hostmask, networkname, channel.getName());
        if (args[0].startsWith("+")) {
            if (QuietTime != null) {
                if (args[0].startsWith("+")) {
                    if (args[1].startsWith("+")) {
                        QuietTime.setTime(QuietTime.getTime() + GeneralUtils.getMilliSeconds(args[1].replace("+", "")));
                    } else if (args[1].startsWith("-")) {
                        QuietTime.setTime(QuietTime.getTime() - GeneralUtils.getMilliSeconds(args[1].replace("-", "")));

                    } else {
                        QuietTime.setTime(GeneralUtils.getMilliSeconds(args[1].replace("-", "")));
                    }
                    IRCUtils.sendMessage(user, network, channel, "Quiet modified", prefix);
                    QuietTimeUtils.saveQuietTimes();
                }
            } else {
                ErrorUtils.sendError(user, "Quiet does not exist!");
            }
        } else if (args[0].startsWith("-")) {
            if (QuietTime != null) {
                QuietTime.setTime(0);
                QuietTimeUtils.saveQuietTimes();
            } else {
                IRCUtils.setMode(channel, network, "-" + Registry.QuietBans.get(ircd), hostmask);
            }
        } else {

            if (QuietTime == null) {
                if (args.length == 2) {
                    IRCUtils.setMode(channel, network, "+" + Registry.QuietBans.get(ircd), hostmask);
                    TimedBan c = new TimedBan(hostmask, networkname, ircd, channel.getName(), GeneralUtils.getMilliSeconds(args[1]), System.currentTimeMillis());
                    Registry.QuietTimes.add(c);
                    QuietTimeUtils.saveQuietTimes();

                } else if (args.length < 2) {
                    IRCUtils.setMode(channel, network, "+" + Registry.QuietBans.get(ircd), hostmask);
                    TimedBan c = new TimedBan(hostmask, networkname, ircd, channel.getName(), GeneralUtils.getMilliSeconds("24h"), System.currentTimeMillis());
                    Registry.QuietTimes.add(c);
                    QuietTimeUtils.saveQuietTimes();

                }
            } else {
                ErrorUtils.sendError(user, "Quiet already exists!");
            }
        }
    }


}
