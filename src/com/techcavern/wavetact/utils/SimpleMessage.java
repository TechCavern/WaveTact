/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.utils;

import org.pircbotx.hooks.events.MessageEvent;

/**
 *
 * @author jztech101
 */
public class SimpleMessage extends Command {

    private String message;

    public SimpleMessage(String i, int p, String m)
    {
        super(i, p);
        this.message = m;
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception{
    {
        event.getChannel().send().message(message);
    }
}
}
