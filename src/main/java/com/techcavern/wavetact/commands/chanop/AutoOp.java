package com.techcavern.wavetact.commands.chanop;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.ChanOPCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.PermUtils;
import com.techcavern.wavetact.utils.databaseUtils.PermChannelUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import com.techcavern.wavetact.utils.objects.PermChannel;
import org.pircbotz.Channel;
import org.pircbotz.PircBotZ;
import org.pircbotz.User;


public class AutoOp extends GenericCommand {
    @CMD
    @ChanOPCMD
    public AutoOp() {
        super(GeneralUtils.toArray("autoop autop ap"), 10, "autoop (-)[user] - Define whether to autoop the user or not");
    }

    @Override
    public void onCommand(User user, PircBotZ Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        String account;
        if (args[0].startsWith("-")) {
            account = PermUtils.getAccount(Bot, args[0].replaceFirst("-", ""));
        } else {
            account = PermUtils.getAccount(Bot, args[0]);
        }
        PermChannel PLChannel = PermChannelUtils.getPermLevelChannel(Bot.getServerInfo().getNetwork(), account, channel.getName());
        if (args[0].startsWith("-")) {
            if (PLChannel != null) {
                PLChannel.setAuto(false);
                PermChannelUtils.savePermChannels();
                channel.send().message(account + " will no longer be auto-opped");
            } else {
                user.send().notice("User is not found on channel access lists");
            }
        } else {
            if (PLChannel != null) {
                PLChannel.setAuto(true);
                PermChannelUtils.savePermChannels();
                channel.send().message(account + " will henceforth be auto-opped");
            } else {
                user.send().notice("User is not found on channel access lists");
            }
        }
    }
}

