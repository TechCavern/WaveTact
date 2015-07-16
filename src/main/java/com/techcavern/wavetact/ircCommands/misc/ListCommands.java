package com.techcavern.wavetact.ircCommands.misc;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.Registry;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jztech101 on 6/23/14.
 */

@SuppressWarnings("ALL")
@IRCCMD
public class ListCommands extends IRCCommand {

    public ListCommands() {
        super(GeneralUtils.toArray("listcommands commands list cmds"), 0, "listcommands [permlevel/all]", "Returns list of Commands specific to that permlevel or all ircCommands", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        int permlevel = 0;
        if (args.length > 0) {
            try {
                permlevel = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                permlevel = 0;
                return;
            }
        }
        final int fpermlevel = permlevel;
        List<String> commands = Registry.ircCommandList.stream().filter(cmd -> cmd.getPermLevel() == fpermlevel).map(IRCCommand::getCommand).collect(Collectors.toList());
        if (commands.isEmpty()) {
            ErrorUtils.sendError(user, "No commands found with that perm level");
        } else {
            Collections.sort(commands);
            IRCUtils.sendMessage(user, network, channel, StringUtils.join(commands, ", "), prefix);
        }
    }
}

