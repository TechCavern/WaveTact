package com.techcavern.wavetact.commands.chanfounder;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.utils.PermUtils;
import com.techcavern.wavetact.utils.objects.Command;
import com.techcavern.wavetact.utils.objects.PermChannel;
import com.techcavern.wavetact.utils.objects.objectUtils.PermChannelUtils;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Created by jztech101 on 7/5/14.
 */
public class CPermLevel extends Command {
    @CMD
    public CPermLevel() {
        super(GeneralUtils.toArray("permlevel pl"), 18, "permlevel (+)(-)[user] (permlevel) - adds, removes, modifies permissions");
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {
    int c = 0;

        if(args.length > 1) {
            c = Integer.parseInt(args[1]);
    }
        if (c <= 18) {
            if(PermUtils.getAccount(event.getBot(), GetUtils.getUserByNick(event.getChannel(), args[0].replaceFirst("-", ""))) != null || PermUtils.getAccount(event.getBot(), GetUtils.getUserByNick(event.getChannel(), args[0].replaceFirst("\\+", ""))) != null) {
                if (args[0].startsWith("-")) {
                    PermChannel PLChannel = PermChannelUtils.getPermLevelChannel(event.getBot().getServerInfo().getNetwork(), PermUtils.getAccount(event.getBot(), GetUtils.getUserByNick(event.getChannel(), args[0].replaceFirst("-", ""))), event.getChannel().getName());
                    if (PLChannel != null) {
                        GeneralRegistry.PermChannels.remove(PLChannel);
                        PermChannelUtils.savePermChannels();
                        event.getChannel().send().message("User Removed");
                    } else {
                        event.getChannel().send().message("User is not found on channel access lists");
                    }
                } else if (args[0].startsWith("+")) {
                    PermChannel PLChannel = PermChannelUtils.getPermLevelChannel(event.getBot().getServerInfo().getNetwork(), PermUtils.getAccount(event.getBot(), GetUtils.getUserByNick(event.getChannel(), args[0].replaceFirst("\\+", ""))), event.getChannel().getName());
                    if (PLChannel != null) {
                        PLChannel.setPermLevel(Integer.parseInt(args[1]));
                        PermChannelUtils.savePermChannels();
                        event.getChannel().send().message("User Modified");
                    } else {
                        event.getChannel().send().message("User is not found on channel access lists");
                    }


                } else {
                    PermChannel PLChannel = PermChannelUtils.getPermLevelChannel(event.getBot().getServerInfo().getNetwork(), PermUtils.getAccount(event.getBot(), GetUtils.getUserByNick(event.getChannel(), args[0])), event.getChannel().getName());
                    if (PLChannel == null) {
                            GeneralRegistry.PermChannels.add(new PermChannel(event.getChannel().getName(), Integer.parseInt(args[1]), false, event.getBot().getServerInfo().getNetwork(),PermUtils.getAccount(event.getBot(), GetUtils.getUserByNick(event.getChannel(), args[0])) ));
                            PermChannelUtils.savePermChannels();
                            event.getChannel().send().message("User Added");


                    } else {
                        event.getChannel().send().message("User is already in channel access lists!");
                    }
                }
            }else{
                event.getChannel().send().message("User is not registered with NickServ");
            }
        } else {
            event.getChannel().send().message("Permission Denied");
        }
    }
}
