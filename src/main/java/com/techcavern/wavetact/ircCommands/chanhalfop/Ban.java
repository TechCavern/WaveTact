package com.techcavern.wavetact.ircCommands.chanhalfop;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.DatabaseUtils;
import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.jooq.Record;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import static com.techcavern.wavetactdb.Tables.BANS;

@IRCCMD

public class Ban extends IRCCommand {

    public Ban() {
        super(GeneralUtils.toArray("ban unban"), 7, "ban (m) (+)[user][hostmask] (-)(+)(time)", "Bans a user for a specified period of time or 24 hours, if the first parameter is m, the ban will be a mute ban", true);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        String hostmask;
        boolean isMute = false;
        if (args[0].equalsIgnoreCase("m")) {
            isMute = true;
            args = ArrayUtils.remove(args, 0);
        }
        String ban = "b ";
        if (isMute) {
            if (network.getServerInfo().getChannelModes().contains("q")) {
                ban = "q ";
            } else if (network.getServerInfo().getExtBanPrefix() != null && network.getServerInfo().getExtBanPrefix().equalsIgnoreCase("~") && network.getServerInfo().getExtBanList() != null && network.getServerInfo().getExtBanList().contains("q")) {
                ban = "b ~q:";
            } else if (network.getServerInfo().getExtBanList().contains("m") && network.getServerInfo().getExtBanPrefix() == null) {
                ban = "b m:";
            } else {
                ErrorUtils.sendError(user, "This networks ircd is not supported for quiets.");
                return;
            }
        }

        if (args[0].contains("!") && args[0].contains("@")) {
            if (args[0].startsWith("+")) {
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
        String networkname = IRCUtils.getNetworkNameByNetwork(network);
        Record BanRecord = DatabaseUtils.getBan(networkname, channel.getName(), hostmask, isMute);
        if (command.equalsIgnoreCase("unban")) {
            if (BanRecord != null) {
                DatabaseUtils.removeBan(networkname, channel.getName(), hostmask, isMute);
            }
            IRCUtils.setMode(channel, network, "-" + ban, hostmask);

        } else if (args[0].startsWith("+")) {
            if (BanRecord != null) {
                if (args[0].startsWith("+")) {
                    if (args[1].startsWith("+")) {
                        BanRecord.setValue(BANS.TIME, BanRecord.getValue(BANS.TIME) + GeneralUtils.getMilliSeconds(args[1].replace("+", "")));
                    } else if (args[1].startsWith("-")) {
                        BanRecord.setValue(BANS.TIME, BanRecord.getValue(BANS.TIME) - GeneralUtils.getMilliSeconds(args[1].replace("-", "")));
                    } else {
                        BanRecord.setValue(BANS.TIME, GeneralUtils.getMilliSeconds(args[1].replace("+", "")));
                    }
                    IRCUtils.sendMessage(user, network, channel, "Ban modified", prefix);
                }
                DatabaseUtils.updateBan(BanRecord);
            } else {
                ErrorUtils.sendError(user, "Ban does not exist!");
            }
        } else {
            if (BanRecord == null) {
                if (args.length == 2) {
                    DatabaseUtils.addBan(networkname, channel.getName(), hostmask, System.currentTimeMillis(), GeneralUtils.getMilliSeconds(args[1]), isMute, ban);
                } else if (args.length < 2) {
                    DatabaseUtils.addBan(networkname, channel.getName(), hostmask, System.currentTimeMillis(), GeneralUtils.getMilliSeconds("24h"), isMute, ban);
                }
                IRCUtils.setMode(channel, network, "+" + ban, hostmask);
            } else {
                ErrorUtils.sendError(user, "Ban already exists!");
            }
        }
    }
}
