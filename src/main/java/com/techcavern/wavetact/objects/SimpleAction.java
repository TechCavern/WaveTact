/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.objects;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.Arrays;

/**
 * @author jztech101
 */
public class SimpleAction extends Command {

    private final String action;
    private boolean locked;

    public SimpleAction(String i, int p, String a, boolean b) {
        super(i, p, "A Basic Command that takes no Arguments");
        System.out.println("Created Simple Action: " + i);
        this.action = a;
        this.locked = b;
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {
        String daction;
        if(this.action.contains("$1")) {
            daction = this.action.replace("$1", args[0]);
            daction = action.replace("$*", StringUtils.join(Arrays.asList(ArrayUtils.remove(args, 0)), " "));
        }else if (this.action.contains("$*")){
            daction = this.action.replace("$*", StringUtils.join(Arrays.asList(args), " "));
        }else{
            daction = this.action;
        }
        event.getChannel().send().action(daction);
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
