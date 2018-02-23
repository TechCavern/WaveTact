package com.techcavern.wavetact.ircCommands.chanhalfop;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.DatabaseUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.jooq.Record;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import static com.techcavern.wavetactdb.Tables.BANS;
import static com.techcavern.wavetactdb.Tables.CHANNELPROPERTY;

@IRCCMD

public class Ban extends IRCCommand {

    public Ban() {
        super(GeneralUtils.toArray("ban kban quiet unquiet kickban unban mute unmute"), 7, "ban (+)[user][hostmask] (-)(+)(time)", "Bans a user for a specified period of time or 24 hours, if the first parameter is m, the ban will be a mute ban", true);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        String hostmask;
        boolean isMute = false;
        boolean isUser = false;
        if (command.contains("mute")||command.contains("quiet")) {
            isMute = true;
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
                IRCUtils.sendError(user, network, channel, "This networks ircd is not supported for mute bans.", prefix);
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
            isUser = true;
            if (args[0].startsWith("+")) {
                hostmask = IRCUtils.getHostmask(network, args[0].replaceFirst("\\+", ""), true);
            } else {
                hostmask = IRCUtils.getHostmask(network, args[0], true);
            }
        }
        String networkname = IRCUtils.getNetworkNameByNetwork(network);
        Record BanRecord = DatabaseUtils.getBan(networkname, channel.getName(), hostmask, isMute);
        if (command.equalsIgnoreCase("unban") || command.equalsIgnoreCase("unmute") || command.equalsIgnoreCase("unquiet")) {
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
                    if (isPrivate)
                        IRCUtils.sendMessage(user, network, null, "Ban modified", prefix);
                    else
                        IRCUtils.sendMessage(user, network, channel, "Ban modified", prefix);
                }
                DatabaseUtils.updateBan(BanRecord);
            } else {
                IRCUtils.sendError(user, network, channel, "Ban does not exist!", prefix);
            }
        } else {
            if (BanRecord == null) {
                if (args.length >= 2) {
                    DatabaseUtils.addBan(networkname, channel.getName(), hostmask, System.currentTimeMillis(), GeneralUtils.getMilliSeconds(args[1]), isMute, ban);
                } else if (args.length < 2) {
                    Record autounban = DatabaseUtils.getChannelProperty(IRCUtils.getNetworkNameByNetwork(network), channel.getName(), "autounban");
                    if (autounban != null)
                        DatabaseUtils.addBan(networkname, channel.getName(), hostmask, System.currentTimeMillis(), GeneralUtils.getMilliSeconds(autounban.getValue(CHANNELPROPERTY.VALUE)), isMute, ban);
                    else
                        DatabaseUtils.addBan(networkname, channel.getName(), hostmask, System.currentTimeMillis(), GeneralUtils.getMilliSeconds("12h"), isMute, ban);
                }
                IRCUtils.setMode(channel, network, "+" + ban + hostmask, null);
                if ((command.equalsIgnoreCase("kban") || command.equalsIgnoreCase("kickban")) && isUser && channel.getUsersNicks().contains(args[0])) {
                    String nick = args[0];
                    if (nick.equalsIgnoreCase(network.getNick()))
                        IRCUtils.sendKick(network.getUserBot(), user, network, channel, "[" + user.getNick() + "] Banned - Your behavior is not conducive to the desired environment");
                    else
                        IRCUtils.sendKick(network.getUserBot(), IRCUtils.getUserByNick(network, nick), network, channel, "[" + user.getNick() + "] Banned - Your behavior is not conducive to the desired environment");
                }
            } else {
                IRCUtils.sendError(user, network, channel, "Ban already exists!", prefix);
            }
        }
    }
}
