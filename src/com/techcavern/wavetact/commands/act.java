/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands;

import com.techcavern.wavetact.utils.Command;
import com.techcavern.wavetact.utils.GeneralRegistry;
import org.pircbotx.hooks.events.MessageEvent;

/**
 *
 * @author jztech101
 */
public class act extends Command{
    public act(){
        super("act", 5);
    }
    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception{
                    event.getChannel().send().action(event.getMessage().replace(GeneralRegistry.CommandChar+"act ",""));
				
    }
}
