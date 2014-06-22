/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.chanop;

import com.techcavern.wavetact.objects.Command;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * @author jztech101
 */
public class Part extends Command {

    public Part() {
        super("part", 5);
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {
        event.getChannel().send().part();
    }
}
