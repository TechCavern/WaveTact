/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.utils;

import com.techcavern.wavetact.objects.Command;
import org.apache.commons.lang3.ArrayUtils;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * @author jztech101
 */
public class MessageListener extends ListenerAdapter<PircBotX> {

    @Override
    public void onMessage(MessageEvent<PircBotX> event) throws Exception {
        String[] messageParts = event.getMessage().split(" ");
        String m = messageParts[0].toLowerCase();
        for (int c = 0; c < GeneralRegistry.Commands.size(); c++) {
            Command Command = GeneralRegistry.Commands.get(c);
            if (m.equalsIgnoreCase(GeneralRegistry.CommandChar + Command.getCommandID())) {
                if (PermUtils.getPermLevel(event.getBot(), event.getUser(), event.getChannel()) >= Command.getPermLevel()) {
                    if (Command.getPermLevel() == 10) {
                        if (event.getChannel().isOp(event.getBot().getUserBot()) || event.getChannel().isOwner(event.getBot().getUserBot())) {
                            Command.onCommand(event, ArrayUtils.remove(messageParts, 0));
                        } else {
                            event.getChannel().send().message("Error: I must be Opped to perform the operation requested");
                        }
                    } else if (Command.getPermLevel() == 15) {

                        if (event.getChannel().isOwner(event.getBot().getUserBot())) {
                            Command.onCommand(event, ArrayUtils.remove(messageParts, 0));
                        } else {
                            event.getChannel().send().message("Error: I must be Ownered to perform the operation requested");
                        }
                    } else {
                        Command.onCommand(event, ArrayUtils.remove(messageParts, 0));

                    }

                } else {
                    event.getChannel().send().message("Permission Denied");

                }
            }
        }
    }

}
