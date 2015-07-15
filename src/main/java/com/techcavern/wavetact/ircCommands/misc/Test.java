package com.techcavern.wavetact.ircCommands.misc;

import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

//@IRCCMD
public class Test extends IRCCommand {

    public Test() {
        super(GeneralUtils.toArray("test dev"), 0, "test", "Test Command (This should not show up in a production environment. If it does, report it", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        boolean isAction = false;
        String message = GeneralUtils.buildMessage(1, args.length, args);
        if (args[0].startsWith("+")) {
            isAction = true;
            args[0] = args[0].replaceFirst("\\+", "");
        }
        int times = Integer.parseInt(args[0]);
        args = ArrayUtils.remove(args, 0);
        if (args[0].startsWith("+")) {
            message = GeneralUtils.prism(message.replaceFirst("\\+", ""));
        }
        if (times <= userPermLevel / 2) {
            if (isAction)
                for (int i = 0; i < times; i++)
                    IRCUtils.sendAction(network, channel, message, prefix);
            else
                for (int i = 0; i < times; i++)
                    IRCUtils.sendMessage(network, channel, message, prefix);

        } else {
            ErrorUtils.sendError(user, "Permission Denied");
        }
    }
}
