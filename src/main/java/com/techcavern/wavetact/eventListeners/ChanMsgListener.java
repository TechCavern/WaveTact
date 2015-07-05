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
import static com.techcavern.wavetactdb.Tables.NETWORKPROPERTY;

/**
 * @author jztech101
 */
public class ChanMsgListener extends ListenerAdapter {
    @Override
    public void onMessage(MessageEvent event) throws Exception {
        class process implements Runnable {
            public void run() {
                String[] message = StringUtils.split(Colors.removeFormattingAndColors(event.getMessage()), " ");
                if(event.getMessage().startsWith(DatabaseUtils.getNetworkProperty(IRCUtils.getNetworkNameByNetwork(event.getBot()), "commandchar").getValue(NETWORKPROPERTY.VALUE))){
                String chancommand = StringUtils.replaceOnce(message[0].toLowerCase(), DatabaseUtils.getNetworkProperty(IRCUtils.getNetworkNameByNetwork(event.getBot()), "commandchar").getValue(NETWORKPROPERTY.VALUE), "");
                message = ArrayUtils.remove(message, 0);
                IRCCommand Command = IRCUtils.getCommand(chancommand, IRCUtils.getNetworkNameByNetwork(event.getBot()), event.getChannel().getName());
                if (Command != null) {
                    int userPermLevel = PermUtils.getPermLevel(event.getBot(), event.getUser().getNick(), event.getChannel());
                    if (userPermLevel >= Command.getPermLevel()) {
                        try {
                            Command.onCommand(chancommand, event.getUser(), event.getBot(), IRCUtils.getPrefix(event.getBot(), event.getChannelSource()), event.getChannel(), false, userPermLevel, message);
                        } catch (Exception e) {
                            ErrorUtils.sendError(event.getUser(), "Failed to execute command, please make sure you are using the correct syntax (" + Command.getSyntax() + ")");
                            e.printStackTrace();
                        }
                    } else {
                        ErrorUtils.sendError(event.getUser(), "Permission denied");
                    }
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
                    if(relayedmessage[0].startsWith(DatabaseUtils.getNetworkProperty(IRCUtils.getNetworkNameByNetwork(event.getBot()), "commandchar").getValue(NETWORKPROPERTY.VALUE)))
                    {
                        String relayedcommand = StringUtils.replaceOnce(relayedmessage[0], DatabaseUtils.getNetworkProperty(IRCUtils.getNetworkNameByNetwork(event.getBot()), "commandchar").getValue(NETWORKPROPERTY.VALUE), "");
                        relayedmessage = ArrayUtils.remove(relayedmessage, 0);
                        IRCCommand Command = IRCUtils.getCommand(relayedcommand, IRCUtils.getNetworkNameByNetwork(event.getBot()), event.getChannel().getName());
                        if (Command != null && Command.getPermLevel() == 0) {
                            try {
                                Command.onCommand(relayedcommand, event.getUser(), event.getBot(), IRCUtils.getPrefix(event.getBot(), event.getChannelSource()), event.getChannel(), false, 0, relayedmessage);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }
            }
        }
        Registry.threadPool.execute(new process());
    }
}







