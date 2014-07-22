package com.techcavern.wavetact.commands.chanfounder;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.ChanFounderCMD;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.PermUtils;
import com.techcavern.wavetact.utils.databaseUtils.PermChannelUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import com.techcavern.wavetact.utils.objects.PermChannel;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

/**
 * Created by jztech101 on 7/5/14.
 */
public class CPermLevel extends GenericCommand {
    @CMD
    @ChanFounderCMD
    public CPermLevel() {
        super(GeneralUtils.toArray("permlevel pl cpermlevel"), 18, "permlevel (+)(-)[user] (permlevel) - adds, removes, modifies permissions");
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
                account = PermUtils.getAccount(Bot, args[0].replaceFirst("-", ""));
            } else if (args[0].startsWith("+")) {
                account = PermUtils.getAccount(Bot, args[0].replaceFirst("\\+", ""));
            } else {
                account = PermUtils.getAccount(Bot, args[0]);
            }
            PermChannel PLChannel = PermChannelUtils.getPermLevelChannel(Bot.getServerInfo().getNetwork(), account, channel.getName());
            if (account != null) {
                if (args[0].startsWith("-")) {
                    if (PLChannel != null) {
                        GeneralRegistry.PermChannels.remove(PLChannel);
                        PermChannelUtils.savePermChannels();
                        channel.send().message(args[0].replaceFirst("-", "") + " Removed from access lists");
                    } else {
                        user.send().notice("User is not found on channel access lists");
                    }
                } else if (args[0].startsWith("+")) {
                    if (PLChannel != null) {
                        PLChannel.setPermLevel(Integer.parseInt(args[1]));
                        PermChannelUtils.savePermChannels();
                        channel.send().message(args[0].replaceFirst("\\+", "") + " Modified in access lists");
                    } else {
                        user.send().notice("User is not found on channel access lists");
                    }


                } else {
                    if (PLChannel == null) {
                        GeneralRegistry.PermChannels.add(new PermChannel(channel.getName(), Integer.parseInt(args[1]), false, Bot.getServerInfo().getNetwork(), account));
                        PermChannelUtils.savePermChannels();
                        channel.send().message(args[0] + " added to access lists");


                    } else {
                        user.send().notice("User is already in channel access lists!");
                    }
                }
            } else {
                user.send().notice("User is not registered with NickServ");
            }
        } else {
            user.send().notice("Globals & Controllers must be registered by the controller");
        }
    }
}
