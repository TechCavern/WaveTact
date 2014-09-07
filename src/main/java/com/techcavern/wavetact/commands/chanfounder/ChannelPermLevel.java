package com.techcavern.wavetact.commands.chanfounder;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.ChanFounderCMD;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.PermUtils;
import com.techcavern.wavetact.utils.databaseUtils.PermChannelUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import com.techcavern.wavetact.utils.objects.PermChannel;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@CMD
@ChanFounderCMD
public class ChannelPermLevel extends GenericCommand {

    public ChannelPermLevel() {
        super(GeneralUtils.toArray("channelpermlevel chanpermlevel cpl cpermlevel"), 18, "channelpermlevel (+)(-)[user] (permlevel)", "adds, removes, modifies permissions");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        int c = 0;

        if (args.length > 1) {
            c = Integer.parseInt(args[1]);
        }
        if (c <= 18) {
            String account;
            if (args[0].startsWith("-")) {
                account = args[0].replaceFirst("-", "");
            } else if (args[0].startsWith("+")) {
                account = args[0].replaceFirst("\\+", "");
            } else {
                account = args[0];
            }
            String auth = PermUtils.getAuthedAccount(Bot, account);
            if (auth != null) {
                account = auth;
            }
            PermChannel PLChannel = PermChannelUtils.getPermLevelChannel(Bot.getServerInfo().getNetwork(), account, channel.getName());
            if (account != null) {
                if (args[0].startsWith("-")) {
                    if (PLChannel != null) {
                        GeneralRegistry.PermChannels.remove(PLChannel);
                        PermChannelUtils.savePermChannels();
                        channel.send().message(args[0].replaceFirst("-", "") + " Removed from access lists");
                    } else {
                        IRCUtils.sendError(user, "User is not found on channel access lists");
                    }
                } else if (args[0].startsWith("+")) {
                    if (PLChannel != null) {
                        PLChannel.setPermLevel(Integer.parseInt(args[1]));
                        PermChannelUtils.savePermChannels();
                        channel.send().message(args[0].replaceFirst("\\+", "") + " Modified in access lists");
                    } else {
                        IRCUtils.sendError(user, "User is not found on channel access lists");
                    }


                } else {
                    if (PLChannel == null) {
                        GeneralRegistry.PermChannels.add(new PermChannel(channel.getName(), Integer.parseInt(args[1]), false, Bot.getServerInfo().getNetwork(), account));
                        PermChannelUtils.savePermChannels();
                        channel.send().message(args[0] + " added to access lists");


                    } else {
                        IRCUtils.sendError(user, "User is already in channel access lists!");
                    }
                }
            } else {
                IRCUtils.sendError(user, "User is not registered");
            }
        } else {
            IRCUtils.sendError(user, "NetworkAdmins & Controllers must be registered by the controller");
        }
    }
}
