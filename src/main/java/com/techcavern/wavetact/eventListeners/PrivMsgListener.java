/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.eventListeners;

import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.DatabaseUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.PermUtils;
import com.techcavern.wavetact.utils.Registry;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Record;
import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.PrivateMessageEvent;

import static com.techcavern.wavetactdb.Tables.NETWORKPROPERTY;

/**
 * @author jztech101
 */
public class PrivMsgListener extends ListenerAdapter {

    @Override
    public void onPrivateMessage(PrivateMessageEvent event) throws Exception {
        class process implements Runnable {
            public void run() {
                String[] message = StringUtils.split(Colors.removeFormatting(event.getMessage()), " ");
                String commandchar = IRCUtils.getCommandChar(event.getBot(), null);
                String privcommand = message[0].toLowerCase();
                IRCUtils.sendLogChanMsg(event.getBot(), "[PM] " + IRCUtils.noPing(event.getUser().getNick())+"!" + event.getUser().getLogin()+ "@" + event.getUser().getHostname() + ": " + event.getMessage());
                if (commandchar != null)
                    privcommand = StringUtils.replaceOnce(privcommand, commandchar, "");
                IRCCommand Command = IRCUtils.getCommand(privcommand, IRCUtils.getNetworkNameByNetwork(event.getBot()), null);
                message = ArrayUtils.remove(message, 0);
                if (Command != null) {
                    String logmsg = StringUtils.join(message, " ");
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
                                    Command.onCommand(privcommand, event.getUser(), event.getBot(), prefix, channel, true, userPermLevel, message);
                                } catch (Exception e) {
                                    IRCUtils.sendError(event.getUser(), event.getBot(), null, "Failed to execute command, please make sure you are using the correct syntax (" + Command.getSyntax() + ")", "");
                                    e.printStackTrace();
                                }
                            } else {
                                IRCUtils.sendError(event.getUser(), event.getBot(), null, "Permission denied", "");
                            }
                        } else {
                            IRCUtils.sendError(event.getUser(), event.getBot(), null, "Please specify channel as argument #1 in front of all the other arguments", "");
                        }
                    } else {
                        int userPermLevel = PermUtils.getPermLevel(event.getBot(), event.getUser().getNick(), null);
                        if (Command.getPermLevel() == 0) {
                            try {
                                Command.onCommand(privcommand, event.getUser(), event.getBot(), null, null, true, userPermLevel, message);
                            } catch (Exception e) {
                                IRCUtils.sendError(event.getUser(), event.getBot(), null, "Failed to execute command, please make sure you are using the correct syntax (" + Command.getSyntax() + ")", "");
                                e.printStackTrace();
                            }
                        } else if (Command.getPermLevel() <= 5 && userPermLevel >= 1) {
                            try {
                                Command.onCommand(privcommand, event.getUser(), event.getBot(), null, null, true, userPermLevel, message);
                            } catch (Exception e) {
                                IRCUtils.sendError(event.getUser(), event.getBot(), null, "Failed to execute command, please make sure you are using the correct syntax (" + Command.getSyntax() + ")", "");
                                e.printStackTrace();
                            }
                        } else {
                            if (userPermLevel >= Command.getPermLevel()) {
                                try {
                                    Command.onCommand(privcommand, event.getUser(), event.getBot(), null, null, true, userPermLevel, message);
                                } catch (Exception e) {
                                    IRCUtils.sendError(event.getUser(), event.getBot(), null, "Failed to execute command, please make sure you are using the correct syntax (" + Command.getSyntax() + ")", "");
                                    e.printStackTrace();
                                }
                            } else {
                                IRCUtils.sendError(event.getUser(), event.getBot(), null, "Permission denied", "");
                            }
                        }
                    }
                }
            }

        }
        Registry.threadPool.execute(new process());
    }
}




