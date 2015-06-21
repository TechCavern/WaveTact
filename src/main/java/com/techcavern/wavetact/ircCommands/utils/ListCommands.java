package com.techcavern.wavetact.ircCommands.utils;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.Registry;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.ArrayList;
import java.util.List;

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
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        int permlevel = 0;
        if (args.length > 0) {
            try {
                permlevel = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                permlevel = 20;
                return;
            }
        }
        List<String> commands = new ArrayList<>();
        for (IRCCommand cmd : Registry.IRCCommands) {
            if (cmd.getPermLevel() <= permlevel)
                commands.add(cmd.getCommand());
        }
        IRCUtils.sendMessage(user, network, channel, StringUtils.join(commands, ", "), prefix);
    }
}

