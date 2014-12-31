package com.techcavern.wavetact.runnables;

import com.techcavern.wavetact.utils.*;
import com.techcavern.wavetact.objects.IRCCommand;
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
                String command = message[0].toLowerCase();
                IRCCommand Command = IRCUtils.getCommand(command, IRCUtils.getNetworkNameByNetwork(event.getBot()), null);
                if (Command == null) {
                    Command = IRCUtils.getCommand(StringUtils.replaceOnce(command, DatabaseUtils.getConfig("commandchar"), ""), IRCUtils.getNetworkNameByNetwork(event.getBot()), null);                }
                message = ArrayUtils.remove(message, 0);
                if (Command != null) {
                    if (Command.getChannelRequired()) {
                        Channel channel = null;
                        String prefix = null;
                        if (message.length > 0) {
                            prefix = IRCUtils.getPrefix(event.getBot(), message[0]);
                            if (!prefix.isEmpty())
                                channel = IRCUtils.getChannelbyName(event.getBot(), message[0].replace(prefix, ""));
                            else
                                channel = IRCUtils.getChannelbyName(event.getBot(), message[0]);
                            message = ArrayUtils.remove(message, 0);
                        }
                        if (channel != null) {
                            int userPermLevel = PermUtils.getPermLevel(event.getBot(), event.getUser().getNick(), channel);
                            if (userPermLevel >= Command.getPermLevel()) {
                                try {
                                    Command.onCommand(event.getUser(), event.getBot(), prefix + channel.getName(), channel, true, userPermLevel, message);
                                } catch (Exception e) {
                                    ErrorUtils.sendError(event.getUser(), "Failed to execute command, please make sure you are using the correct syntax (" + Command.getSyntax() + ")");
                                    e.printStackTrace();
                                }
                            } else {
                                ErrorUtils.sendError(event.getUser(), "Permission denied");
                            }
                        } else {
                            ErrorUtils.sendError(event.getUser(), "Please specify channel as argument #1 in front of all the other arguments");
                        }
                    } else {
                        int userPermLevel = PermUtils.getPermLevel(event.getBot(), event.getUser().getNick(), null);
                        if (Command.getPermLevel() <= 5) {
                            try {
                                Command.onCommand(event.getUser(), event.getBot(), null, null, true, 5, message);
                            } catch (Exception e) {
                                ErrorUtils.sendError(event.getUser(), "Failed to execute command, please make sure you are using the correct syntax (" + Command.getSyntax() + ")");
                                e.printStackTrace();
                            }
                        } else {
                            if (userPermLevel >= Command.getPermLevel()) {
                                try {
                                    Command.onCommand(event.getUser(), event.getBot(), null, null, true, 5, message);
                                } catch (Exception e) {
                                    ErrorUtils.sendError(event.getUser(), "Failed to execute command, please make sure you are using the correct syntax (" + Command.getSyntax() + ")");
                                    e.printStackTrace();
                                }
                            } else {
                                ErrorUtils.sendError(event.getUser(), "Permission denied");
                            }
                        }
                    }
                }
            }

        }
        Registry.threadPool.execute(new process());
    }
}