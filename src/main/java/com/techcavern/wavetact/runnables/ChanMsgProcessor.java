package com.techcavern.wavetact.runnables;

import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.*;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Colors;
import org.pircbotx.hooks.events.MessageEvent;

import static com.techcavern.wavetactdb.Tables.RELAYBOTS;


public class ChanMsgProcessor {
    public static void ChanMsgProcess(final MessageEvent event) {
        class process implements Runnable {
            public void run() {
                String[] message = StringUtils.split(Colors.removeFormattingAndColors(event.getMessage()), " ");
                String command = message[0].toLowerCase();
                message = ArrayUtils.remove(message, 0);
                IRCCommand Command = IRCUtils.getCommand(StringUtils.replaceOnce(command, DatabaseUtils.getConfig("commandchar"), ""), IRCUtils.getNetworkNameByNetwork(event.getBot()), event.getChannel().getName());
                if (Command != null && command.startsWith(DatabaseUtils.getConfig("commandchar"))) {
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
                String relaysplit = DatabaseUtils.getRelayBot(IRCUtils.getNetworkNameByNetwork(event.getBot()), event.getChannel().getName(), PermUtils.authUser(event.getBot(), event.getUser().getNick())).getValue(RELAYBOTS.VALUE);
                String startingmessage = event.getMessage();
                if (relaysplit != null) {
                    String[] midmessage = StringUtils.split(startingmessage, relaysplit);
                    if (midmessage.length > 1)
                        startingmessage = midmessage[1];
                    else
                        return;
                } else {
                    return;
                }
                String[] message = StringUtils.split(Colors.removeFormattingAndColors(startingmessage), " ");
                String command = message[0].toLowerCase();
                message = ArrayUtils.remove(message, 0);
                IRCCommand Command = IRCUtils.getCommand(StringUtils.replaceOnce(command, DatabaseUtils.getConfig("commandchar"), ""), IRCUtils.getNetworkNameByNetwork(event.getBot()), event.getChannel().getName());
                if (Command != null && command.startsWith(DatabaseUtils.getConfig("commandchar")) && Command.getPermLevel() == 0) {
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
