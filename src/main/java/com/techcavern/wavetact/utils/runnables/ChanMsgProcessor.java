package com.techcavern.wavetact.utils.runnables;

import com.techcavern.wavetact.utils.*;
import com.techcavern.wavetact.utils.objects.ChannelUserProperty;
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
                GenericCommand Command = GetUtils.getGenericCommand(StringUtils.replaceOnce(command, GetUtils.getCommandChar(event.getBot()), ""));
                if (Command != null && command.startsWith(GetUtils.getCommandChar(event.getBot()))) {
                    int userPermLevel = PermUtils.getPermLevel(event.getBot(), event.getUser().getNick(), event.getChannel());
                    if (userPermLevel >= Command.getPermLevel()) {
                        try {
                            Command.onCommand(event.getUser(), event.getBot(), IRCUtils.getPrefix(event.getBot(), event.getChannelSource()), event.getChannel(), false, userPermLevel, message);
                        } catch (Exception e) {
                            ErrorUtils.sendError(event.getUser(), "Failed to execute command, please make sure you are using the correct syntax (" + Command.getSyntax() + ")");
                            e.printStackTrace();
                        }
                    } else {
                        ErrorUtils.sendError(event.getUser(), "Permission denied");
                    }
                } else {
                    RelayMsgProcess(event);

                }
            }
        }
        Registry.threadPool.execute(new process());
    }
    public static void RelayMsgProcess(final MessageEvent event) {
        class process implements Runnable {
            public void run() {
                ChannelUserProperty relayBot = GetUtils.getRelayBotbyBotName(event.getBot(), event.getChannel().getName(), PermUtils.authUser(event.getBot(), event.getUser().getNick()));
                String startingmessage = event.getMessage();
                if (relayBot != null) {
                    String[] midmessage = StringUtils.split(startingmessage, relayBot.getProperty());
                    if (midmessage.length > 1)
                        startingmessage = midmessage[1];
                    else
                        return;
                }else{
                    return;
                }
                String[] message = StringUtils.split(Colors.removeFormattingAndColors(startingmessage), " ");
                String command = message[0].toLowerCase();
                message = ArrayUtils.remove(message, 0);
                GenericCommand Command = GetUtils.getGenericCommand(StringUtils.replaceOnce(command, GetUtils.getCommandChar(event.getBot()), ""));
                if (Command != null && command.startsWith(GetUtils.getCommandChar(event.getBot())) && Command.getPermLevel() == 0) {
                    try {
                        Command.onCommand(event.getUser(), event.getBot(), IRCUtils.getPrefix(event.getBot(), event.getChannelSource()), event.getChannel(), false, 0, message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        Registry.threadPool.execute(new process());
    }

}
