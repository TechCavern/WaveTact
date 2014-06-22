/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.trusted;

import com.techcavern.wavetact.objects.Command;
import com.techcavern.wavetact.utils.GeneralRegistry;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * @author jztech101
 */
public class Say extends Command {

    public Say() {
        super("say", 5);
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {
        event.getChannel().send().message(event.getMessage().replace(GeneralRegistry.CommandChar + "say ", ""));

    }
}
