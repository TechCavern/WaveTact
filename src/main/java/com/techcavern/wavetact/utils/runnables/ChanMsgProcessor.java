package com.techcavern.wavetact.utils.runnables;

import com.techcavern.wavetact.utils.*;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Colors;
import org.pircbotx.hooks.events.MessageEvent;


public class ChanMsgProcessor {
    public static void ChanMsgProcess(final MessageEvent event) {
        class process implements Runnable {
            public void run() {
                String[] message = StringUtils.split(Colors.removeFormattingAndColors(event.getMessage()), " ");
                String command = message[0].toLowerCase();
                message = ArrayUtils.remove(message, 0);
                GenericCommand Command = GetUtils.getCommand(command.replaceFirst(GetUtils.getCommandChar(event.getBot()), ""));
                if (Command != null && command.startsWith(GetUtils.getCommandChar(event.getBot()))) {
                    int userPermLevel = PermUtils.getPermLevel(event.getBot(), event.getUser().getNick(), event.getChannel());
                    if (userPermLevel >= Command.getPermLevel()) {
                        try {
                            Command.onCommand(event.getUser(), event.getBot(), IRCUtils.getPrefix(event.getChannelSource()), event.getChannel(), false, userPermLevel, message);
                        } catch (Exception e) {
                            ErrorUtils.sendError(event.getUser(), "Failed to execute command, please make sure you are using the correct syntax (" + Command.getSyntax() + ")");
                        }
                    } else {
                        ErrorUtils.sendError(event.getUser(), "Permission Denied");
                    }
                }
            }
        }
        Constants.threadPool.execute(new process());
    }

}
