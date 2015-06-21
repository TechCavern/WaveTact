/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.eventListeners;

import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.*;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Record;
import org.pircbotx.Colors;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import static com.techcavern.wavetactdb.Tables.CHANNELUSERPROPERTY;

/**
 * @author jztech101
 */
public class ChanMsgListener extends ListenerAdapter {
    @Override
    public void onMessage(MessageEvent event) throws Exception {
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
                    Record rec = DatabaseUtils.getChannelUserProperty(IRCUtils.getNetworkNameByNetwork(event.getBot()), event.getChannel().getName(), PermUtils.authUser(event.getBot(), event.getUser().getNick()), "relaybotsplit");
                    if (rec == null)
                        return;
                    String relaysplit = rec.getValue(CHANNELUSERPROPERTY.VALUE);
                    String startingmessage = event.getMessage();
                    if (relaysplit != null) {
                        String[] midmessage = StringUtils.split(startingmessage, relaysplit);
                        if (midmessage.length > 1) {
                            startingmessage = GeneralUtils.buildMessage(1, midmessage.length, midmessage);
                        } else
                            return;
                    } else {
                        return;
                    }
                    String[] relayedmessage = StringUtils.split(Colors.removeFormattingAndColors(startingmessage), " ");
                    String relayedcommand = relayedmessage[0].toLowerCase();
                    relayedmessage = ArrayUtils.remove(relayedmessage, 0);
                    Command = IRCUtils.getCommand(StringUtils.replaceOnce(relayedcommand, DatabaseUtils.getConfig("commandchar"), ""), IRCUtils.getNetworkNameByNetwork(event.getBot()), event.getChannel().getName());
                    if (Command != null && relayedcommand.startsWith(DatabaseUtils.getConfig("commandchar")) && Command.getPermLevel() == 0) {
                        try {
                            Command.onCommand(event.getUser(), event.getBot(), IRCUtils.getPrefix(event.getBot(), event.getChannelSource()), event.getChannel(), false, 0, relayedmessage);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }
        Registry.threadPool.execute(new process());
    }
}







