/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.utils.events;

import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.utils.PermUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.apache.commons.lang3.ArrayUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;

/**
 * @author jztech101
 */
public class PrivateMessageListener extends ListenerAdapter<PircBotX> {

    @Override
    public void onPrivateMessage(PrivateMessageEvent<PircBotX> event) throws Exception {
        String[] messageParts = event.getMessage().split(" ");
        String m = messageParts[0].toLowerCase();
        GenericCommand Command = GetUtils.getCommand(m);
        if(Command == null){
            Command = GetUtils.getCommand(m.replaceFirst(GetUtils.getCommandChar(event.getBot()), ""));
        }
        messageParts = ArrayUtils.remove(messageParts, 0);
        Channel channel = null;
        if(messageParts.length > 1 && messageParts[0].startsWith("#")){
            channel = GetUtils.getChannelbyName(event.getBot(), messageParts[0]);
            messageParts = ArrayUtils.remove(messageParts, 0);
        }
        if (Command != null) {
                    if((Command.getPermLevel() >= 0 && ( Command.getPermLevel() <= 5 || Command.getPermLevel() > 17)||( channel != null) )){
                        int UserPermLevel = PermUtils.getPermLevel(event.getBot(), event.getUser(), channel);
                        if (UserPermLevel >= Command.getPermLevel()) {
                        if (Command.getPermLevel() == 9) {
                            if (channel.isOp(event.getBot().getUserBot()) || channel.isOp(event.getBot().getUserBot()) ||  channel.isOwner(event.getBot().getUserBot())) {
                                Command.onCommand(event.getUser(), event.getBot(), channel, true, UserPermLevel, messageParts);
                            } else {
                                channel.send().message("Error: I must be at least Opped to perform the operation requested");
                            }
                        } else if (Command.getPermLevel() == 14) {

                            if (channel.isOwner(event.getBot().getUserBot())) {
                                Command.onCommand(event.getUser(), event.getBot(), channel, true, UserPermLevel, messageParts);
                            } else {
                                channel.send().message("Error: I must be Ownered to perform the operation requested");
                            }
                        } else if (Command.getPermLevel() == 6) {
                            if (channel.isOwner(event.getBot().getUserBot()) || channel.isOp(event.getBot().getUserBot()) ||channel.isHalfOp(event.getBot().getUserBot())|| channel.isSuperOp(event.getBot().getUserBot())) {
                                Command.onCommand(event.getUser(), event.getBot(), channel, true, UserPermLevel, messageParts);
                            } else {
                                channel.send().message("Error: I must be at least half-opped to perform the operation requested");
                            }
                        }else{
                            Command.onCommand(event.getUser(), event.getBot(), channel, true, UserPermLevel, messageParts);
                        }

                    } else {
                        event.getUser().send().message("Permission Denied");
                    }}else{
                        event.getUser().send().message("Channel Must be Specified as argument #1");
                    }
                }
            }
        }



