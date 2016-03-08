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
                //sends Relay Message
                if (PermUtils.getPermLevel(event.getBot(), event.getUser().getNick(), event.getChannel()) > -4 && IRCUtils.getPrefix(event.getBot(), event.getChannelSource()).isEmpty())
                    IRCUtils.sendRelayMessage(event.getBot(), event.getChannel(), IRCUtils.noPing(event.getUser().getNick()) + ": " + event.getMessage());
                if (PermUtils.getPermLevel(event.getBot(), event.getUser().getNick(), event.getChannel()) > -3){
                    IRCUtils.addVoice(event.getBot(), event.getChannel(), event.getUser());
                }

                //Begin Input Parsing
                String[] message = StringUtils.split(Colors.removeFormatting(event.getMessage()), " ");
                Record commandcharRecord = DatabaseUtils.getNetworkProperty(IRCUtils.getNetworkNameByNetwork(event.getBot()), "commandchar");
                String commandchar;
                if (commandcharRecord == null) {
                    return;
                }
                commandchar = commandcharRecord.getValue(NETWORKPROPERTY.VALUE);

                if (event.getMessage().startsWith(commandchar)) {
                    String chancommand = StringUtils.replaceOnce(message[0].toLowerCase(), DatabaseUtils.getNetworkProperty(IRCUtils.getNetworkNameByNetwork(event.getBot()), "commandchar").getValue(NETWORKPROPERTY.VALUE), "");
                    message = ArrayUtils.remove(message, 0);
                    IRCCommand Command = IRCUtils.getCommand(chancommand, IRCUtils.getNetworkNameByNetwork(event.getBot()), event.getChannel().getName());
                    if (Command != null) {
                        int userPermLevel = PermUtils.getPermLevel(event.getBot(), event.getUser().getNick(), event.getChannel());
                        if (userPermLevel >= Command.getPermLevel()) {
                            try {
                                Command.onCommand(chancommand, event.getUser(), event.getBot(), IRCUtils.getPrefix(event.getBot(), event.getChannelSource()), event.getChannel(), false, userPermLevel, message);
                            } catch (Exception e) {
                                IRCUtils.sendError(event.getUser(), event.getBot(), event.getChannel(), "Failed to execute command, please make sure you are using the correct syntax (" + Command.getSyntax() + ")", IRCUtils.getPrefix(event.getBot(), event.getChannelSource()));
                                e.printStackTrace();
                            }
                        } else {
                            IRCUtils.sendError(event.getUser(), event.getBot(), event.getChannel(), "Permission denied", IRCUtils.getPrefix(event.getBot(), event.getChannelSource()));
                        }
                    }
                } else {
                    Record rec = DatabaseUtils.getChannelUserProperty(IRCUtils.getNetworkNameByNetwork(event.getBot()), event.getChannel().getName(), PermUtils.authUser(event.getBot(), event.getUser().getNick()), "relaybotsplit");
                    if (rec == null)
                        return;
                    String relaysplit = rec.getValue(CHANNELUSERPROPERTY.VALUE);
                    String startingmessage = event.getMessage();
                    if (relaysplit != null) {
                        String[] midmessage = StringUtils.split(Colors.removeFormatting(startingmessage), relaysplit);
                        if (midmessage.length > 1) {
                            startingmessage = GeneralUtils.buildMessage(1, midmessage.length, midmessage);
                        } else
                            return;
                    } else {
                        return;
                    }
                    String[] relayedmessage = StringUtils.split(startingmessage, " ");
                    if (relayedmessage[0].startsWith(commandchar)) {
                        String relayedcommand = StringUtils.replaceOnce(relayedmessage[0], DatabaseUtils.getNetworkProperty(IRCUtils.getNetworkNameByNetwork(event.getBot()), "commandchar").getValue(NETWORKPROPERTY.VALUE), "");
                        relayedmessage = ArrayUtils.remove(relayedmessage, 0);
                        IRCCommand Command = IRCUtils.getCommand(relayedcommand, IRCUtils.getNetworkNameByNetwork(event.getBot()), event.getChannel().getName());
                        if (Command != null && Command.getPermLevel() == 0) {
                            try {
                                Command.onCommand(relayedcommand, event.getUser(), event.getBot(), IRCUtils.getPrefix(event.getBot(), event.getChannelSource()), event.getChannel(), false, 0, relayedmessage);
                            } catch (Exception e) {
                                IRCUtils.sendError(event.getUser(), event.getBot(), event.getChannel(), "Failed to execute command, please make sure you are using the correct syntax (" + Command.getSyntax() + ")", IRCUtils.getPrefix(event.getBot(), event.getChannelSource()));
                            }
                        }

                    }
                }
            }
        }
        Registry.threadPool.execute(new process());
    }
}







