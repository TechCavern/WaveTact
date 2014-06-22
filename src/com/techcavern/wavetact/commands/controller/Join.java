/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.controller;

import com.techcavern.wavetact.utils.Command;

import org.pircbotx.hooks.events.MessageEvent;


/**
 * @author jztech101
 */
public class Join extends Command {
    public Join() {
        super("join", 10);
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args)
            throws Exception {
        event.getBot().sendIRC().joinChannel(args[0]);
    }
}
