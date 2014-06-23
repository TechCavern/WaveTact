/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.objects;

import org.pircbotx.hooks.events.MessageEvent;

/**
 * @author jztech101
 */
public class SimpleAction extends Command {

    private String action;
    private boolean locked;

    public SimpleAction(String i, int p, String a, boolean b) {
        super(i, p, "A Basic Command that takes no Arguments");
        System.out.println("Created Simple Action: " + i);
        this.action = a;
        this.locked = b;
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {
        event.getChannel().send().action(action);
    }

    @Override
    public boolean getLockedStatus() {
        return locked;
    }

    public void lock() {
        this.locked = true;
    }

    public void unlock() {
        this.locked = false;
    }
}
