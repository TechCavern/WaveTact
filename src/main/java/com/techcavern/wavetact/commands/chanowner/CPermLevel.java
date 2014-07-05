package com.techcavern.wavetact.commands.chanowner;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.utils.objects.Command;
import com.techcavern.wavetact.utils.objects.PermChannel;
import com.techcavern.wavetact.utils.objects.PermUser;
import com.techcavern.wavetact.utils.objects.objectUtils.PermUserUtils;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Created by jztech101 on 7/5/14.
 */
public class CPermLevel extends Command {
    @CMD
    public CPermLevel() {
        super(GeneralUtils.toArray("permlevel pl"), 15, "permlevel (+)(-)[user] (permlevel) - adds, removes, modifies permissions");
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {
        int c = Integer.parseInt(GetUtils.getUserByNick(event.getChannel(), args[0]).getLogin());
        if (c > 15) {
            if (args[0].startsWith("-")) {
                PermChannel PLChannel = PermUserUtils.getPermLevelChannel(event.getBot().getServerInfo().getNetwork(), GetUtils.getUserByNick(event.getChannel(), args[0].replaceFirst("-", "")).getLogin(), event.getChannel().getName());
                PermUser PLUser = PermUserUtils.getPermUserbyNick(GetUtils.getUserByNick(event.getChannel(), args[0].replaceFirst("-", "")).getLogin(), event.getBot().getServerInfo().getNetwork());
                if (PLChannel != null) {
                    PLUser.getPermChannel().remove(PLChannel);
                    PermUserUtils.savePermUsers();
                } else {
                    event.getChannel().send().message("User is not found on channel access lists");
                }
            } else if (args[0].startsWith("+")) {
                PermChannel PLChannel = PermUserUtils.getPermLevelChannel(event.getBot().getServerInfo().getNetwork(), GetUtils.getUserByNick(event.getChannel(), args[0].replaceFirst("\\+", "")).getLogin(), event.getChannel().getName());
                PermUser PLUser = PermUserUtils.getPermUserbyNick(GetUtils.getUserByNick(event.getChannel(), args[0].replaceFirst("\\+", "")).getLogin(), event.getBot().getServerInfo().getNetwork());
                if (PLChannel != null) {
                    PLChannel.setPermLevel(Integer.parseInt(args[1]));
                    PermUserUtils.savePermUsers();
                } else {
                    event.getChannel().send().message("User is not found on channel access lists");
                }


            } else {
                PermChannel PLChannel = PermUserUtils.getPermLevelChannel(event.getBot().getServerInfo().getNetwork(), GetUtils.getUserByNick(event.getChannel(), args[0]).getLogin(), event.getChannel().getName());
                PermUser PLUser = PermUserUtils.getPermUserbyNick(GetUtils.getUserByNick(event.getChannel(), args[0]).getLogin(), event.getBot().getServerInfo().getNetwork());
                if (PLChannel != null) {
                    if (PLUser == null) {
                        GeneralRegistry.PermUsers.add(new PermUser(event.getBot().getServerInfo().getNetwork(), null, GetUtils.getUserByNick(event.getChannel(), args[0]).getLogin(), false));
                        PermUser newPLUser = PermUserUtils.getPermUserbyNick(GetUtils.getUserByNick(event.getChannel(), args[0]).getLogin(), event.getBot().getServerInfo().getNetwork());
                        newPLUser.getPermChannel().add(new PermChannel(event.getChannel().getName(), Integer.getInteger(args[1]), false));
                        PermUserUtils.savePermUsers();
                    } else {
                        PermUser newPLUser = PermUserUtils.getPermUserbyNick(GetUtils.getUserByNick(event.getChannel(), args[0]).getLogin(), event.getBot().getServerInfo().getNetwork());
                        newPLUser.getPermChannel().add(new PermChannel(event.getChannel().getName(), Integer.getInteger(args[1]), false));
                        PermUserUtils.savePermUsers();
                    }
                } else {
                    event.getChannel().send().message("User is already in channel access lists!");
                }
            }
        } else {
            event.getChannel().send().message("Permission Denied");
        }
    }
}
