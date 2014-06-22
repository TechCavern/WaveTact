/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.chanop;

import com.techcavern.wavetact.objects.Command;
import com.techcavern.wavetact.utils.IRCUtils;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * @author jztech101
 */
public class Op extends Command {

    public Op() {
        super("op", 10);
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {
        if (args[0].startsWith("-")) {
            event.getChannel().send().deOp(IRCUtils.getUserByNick(event.getChannel(), args[0].replaceFirst("-", "")));
        }else {
            event.getChannel().send().op(IRCUtils.getUserByNick(event.getChannel(), args[0]));
        }
    }
}
