package com.techcavern.wavetact.commands.chanop;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.utils.objects.Command;
import com.techcavern.wavetact.utils.objects.PermChannel;
import com.techcavern.wavetact.utils.objects.objectUtils.PermChannelUtils;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Created by jztech101 on 7/5/14.
 */
public class AutoOp extends Command {
    @CMD
    public AutoOp() {
        super(GeneralUtils.toArray("autoop autop ap"), 10, "autoop (-)[user] - Define whether to autoop the user or not");
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {
        if (args[0].startsWith("-")) {
            PermChannel PLChannel = PermChannelUtils.getPermLevelChannel(event.getBot().getServerInfo().getNetwork(), GetUtils.getUserByNick(event.getChannel(), args[0].replaceFirst("-", "")).getLogin(), event.getChannel().getName());
            if (PLChannel != null) {
                PLChannel.setAuto(false);
                PermChannelUtils.savePermChannels();
                event.getChannel().send().message("User will no longer be auto-opped");
            } else {
                event.getChannel().send().message("User is not found on channel access lists");
            }
        } else {
            PermChannel PLChannel = PermChannelUtils.getPermLevelChannel(event.getBot().getServerInfo().getNetwork(), GetUtils.getUserByNick(event.getChannel(), args[0].replaceFirst("\\+", "")).getLogin(), event.getChannel().getName());
            if (PLChannel != null) {
                PLChannel.setAuto(true);
                PermChannelUtils.savePermChannels();
                event.getChannel().send().message("User will henceforth be auto-opped");

            } else {
                event.getChannel().send().message("User is not found on channel access lists");
            }
        }
    }
}

