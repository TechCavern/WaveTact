/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.controller;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.utils.objects.Command;
import com.techcavern.wavetact.utils.GeneralUtils;
import org.pircbotx.hooks.events.MessageEvent;


/**
 * @author jztech101
 */
public class Join extends Command {
    @CMD
    public Join() {
        super(GeneralUtils.toArray("join jo"), 9001, "join [channel]");
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args)
            throws Exception {
        event.getBot().sendIRC().joinChannel(args[0]);
    }
}
