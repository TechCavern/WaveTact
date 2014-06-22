/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.chanop;

import com.techcavern.wavetact.utils.AbstractCommand;
import com.techcavern.wavetact.utils.IRCUtils;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * @author jztech101
 */
public class Owner extends AbstractCommand {

    public Owner() {
        super("owner", 15);
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {
        if (args[0].startsWith("-")) {
            event.getChannel().send().deOwner(IRCUtils.getUserByNick(event.getChannel(), args[0].replaceFirst("-", "")));
        } else {
            event.getChannel().send().owner(IRCUtils.getUserByNick(event.getChannel(), args[0]));

        }
    }
}
