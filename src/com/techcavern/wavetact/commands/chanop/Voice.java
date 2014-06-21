/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.chanop;

import com.techcavern.wavetact.utils.Command;
import com.techcavern.wavetact.utils.IRCUtils;

import org.pircbotx.hooks.events.MessageEvent;


/**
 *
 * @author jztech101
 */
public class Voice extends Command {
    public Voice() {
        super("voice", 10);
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args)
        throws Exception {
        if (args[0].startsWith("-")) {
            event.getChannel().send()
                 .deVoice(IRCUtils.getUserByNick(event.getChannel(),
                    args[0].replaceFirst("-", "")));
        } else {
            event.getChannel().send()
                 .voice(IRCUtils.getUserByNick(event.getChannel(), args[0]));
        }
    }
}
