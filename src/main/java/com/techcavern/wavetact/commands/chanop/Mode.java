/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.chanop;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.objects.Command;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * @author jztech101
 */
public class Mode extends Command {
    @CMD
    public Mode() {
        super("mode", 10, "mode [modes to set]");
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {
        event.getChannel().send().setMode(args[0]);
    }
}
