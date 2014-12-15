package com.techcavern.wavetact.commands.chanfounder;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.ChanFounderCMD;
import com.techcavern.wavetact.utils.*;
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
        super(GeneralUtils.toArray("channelpermlevel chanpermlevel cpl cpermlevel"), 18, "channelpermlevel (+)(-)[user] (permlevel)", "Adds, removes or modifies permissions", true);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        String networkname = GetUtils.getNetworkNameByNetwork(network);
        int c = 0;
        if (args.length > 1) {
            c = Integer.parseInt(args[1]);
        } else {
            ErrorUtils.sendError(user, "Failed to parse permission level: " + ChannelPermLevel.super.getSyntax());
            return;
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
            String auth = PermUtils.authUser(network, account);
            if (auth != null) {
                account = auth;
            }
            PermChannel PLChannel = PermChannelUtils.getPermLevelChannel(networkname, account, channel.getName());
            if (account != null) {
                if (args[0].startsWith("-")) {
                    if (PLChannel != null) {
                        Constants.PermChannels.remove(PLChannel);
                        PermChannelUtils.savePermChannels();
                        IRCUtils.sendNotice(user, network, channel, args[0].replaceFirst("-", "") + " removed from access lists", "");
                    } else {
                        ErrorUtils.sendError(user, "User is not found on channel access lists");
                    }
                } else if (args[0].startsWith("+")) {
                    if (PLChannel != null) {
                        PLChannel.setPermLevel(Integer.parseInt(args[1]));
                        PermChannelUtils.savePermChannels();
                        IRCUtils.sendNotice(user, network, channel, args[0].replaceFirst("-", "") + " modified in access lists", "");
                    } else {
                        ErrorUtils.sendError(user, "User is not found on channel access lists");
                    }


                } else {
                    if (PLChannel == null) {
                        Constants.PermChannels.add(new PermChannel(channel.getName(), Integer.parseInt(args[1]), false, networkname, account));
                        PermChannelUtils.savePermChannels();
                        IRCUtils.sendNotice(user, network, channel, args[0].replaceFirst("-", "") + " added to access lists", "");
                    } else {
                        ErrorUtils.sendError(user, "User is already in channel access lists!");
                    }
                }
            } else {
                ErrorUtils.sendError(user, "User is not registered");
            }
        } else {
            ErrorUtils.sendError(user, "Network Admins & controllers must be registered by the controller");
        }
    }
}
