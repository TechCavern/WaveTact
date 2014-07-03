/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.chanop;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.objects.Command;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * @author jztech101
 */
public class HalfOp extends Command {
    @CMD
    public HalfOp() {
        super(GeneralUtils.toArray("halfop hop"), 9, "halfop (-)(user to  halfop)");
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {
        if (args[0] != null) {
            if (args[0].equalsIgnoreCase("-")) {
                event.getChannel().send().deHalfOp(event.getUser());
            } else if (args[0].startsWith("-")) {
                event.getChannel().send().deHalfOp(IRCUtils.getUserByNick(event.getChannel(), args[0].replaceFirst("-", "")));
            } else {
                event.getChannel().send().halfOp(IRCUtils.getUserByNick(event.getChannel(), args[0]));

            }
        } else {
            event.getChannel().send().halfOp(event.getUser());
        }
    }
}
