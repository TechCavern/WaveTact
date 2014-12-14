package com.techcavern.wavetact.utils.runnables;

import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.PermUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.hooks.events.PrivateMessageEvent;


public class PrivMsgProcessor {

    public static void PrivMsgProcess(final PrivateMessageEvent event) {
        class process implements Runnable {
            public void run() {
                String[] message = StringUtils.split(Colors.removeFormattingAndColors(event.getMessage()), " ");
                String m = message[0].toLowerCase();
                GenericCommand Command = GetUtils.getCommand(m);
                if (Command == null) {
                    Command = GetUtils.getCommand(m.replaceFirst(GetUtils.getCommandChar(event.getBot()), ""));
                }
                message = ArrayUtils.remove(message, 0);
                if (Command != null) {
                    if (Command.getChannelRequired()) {
                        String prefix = IRCUtils.getPrefix(message[0]);
                        Channel channel = GetUtils.getChannelbyName(event.getBot(), message[0].replace(prefix, ""));
                        if (channel != null) {
                            int userPermLevel = PermUtils.getPermLevel(event.getBot(), event.getUser().getNick(), channel);
                            if (userPermLevel >= Command.getPermLevel()) {
                                try {
                                    Command.onCommand(event.getUser(), event.getBot(), prefix + channel.getName(), channel, true, userPermLevel, ArrayUtils.remove(message, 0));
                                } catch (Exception e) {
                                    IRCUtils.sendError(event.getUser(), "Failed to execute command, please make sure you are using the correct syntax (" + Command.getSyntax() + ")");
                                }
                            } else {
                                IRCUtils.sendError(event.getUser(), "Permission Denied");
                            }
                        } else {
                            IRCUtils.sendError(event.getUser(), "Please specify channel as argument #1 in front of all the other arguments");
                        }
                    } else {
                        int userPermLevel = PermUtils.getPermLevel(event.getBot(), event.getUser().getNick(), null);
                        if (Command.getPermLevel() <= 5) {
                            try {
                                Command.onCommand(event.getUser(), event.getBot(), null, null, true, 5, ArrayUtils.remove(message, 0));
                            } catch (Exception e) {
                                IRCUtils.sendError(event.getUser(), "Failed to execute command, please make sure you are using the correct syntax (" + Command.getSyntax() + ")");
                            }
                        } else {
                            if (userPermLevel >= Command.getPermLevel()) {
                                try {
                                    Command.onCommand(event.getUser(), event.getBot(), null, null, true, 5, ArrayUtils.remove(message, 0));
                                } catch (Exception e) {
                                    IRCUtils.sendError(event.getUser(), "Failed to execute command, please make sure you are using the correct syntax (" + Command.getSyntax() + ")");
                                }
                            } else {
                                IRCUtils.sendError(event.getUser(), "Permission Denied");
                            }
                        }
                    }
                }
                GeneralRegistry.threadPool.execute(new process());
            }

        }
    }
}