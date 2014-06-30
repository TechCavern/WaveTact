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
public class SimpleMessage extends Command {

    private final String message;
    private boolean locked;

    public SimpleMessage(String i, int p, String m, boolean b) {
        super(i, p, "A Basic Command");
        this.message = m;
        this.locked = b;
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {
        String dresponse;
        if(this.message.contains("$1")) {
            dresponse = this.message.replace("$1", args[0]);
            dresponse = dresponse.replace("$*", StringUtils.join(Arrays.asList(ArrayUtils.remove(args,0)), " "));
        }else if (message.contains("$*")){
             dresponse = this.message.replace("$*", StringUtils.join(Arrays.asList(args), " "));
        }else{
             dresponse = this.message;
        }
        event.respond(dresponse);
    }

    public String getMessage() {
        return this.message;
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
