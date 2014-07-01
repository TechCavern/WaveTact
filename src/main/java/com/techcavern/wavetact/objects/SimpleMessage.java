/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.objects;

import com.techcavern.wavetact.utils.GeneralUtils;
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
        String dresponse = this.message;
        String[] message = StringUtils.split(this.message, "");
        int i = 0;
        for(String g:message){
            if(g.startsWith("$") &&!g.contains("*")){
                dresponse = dresponse.replace(g,args[Integer.parseInt(g.replace("$", ""))-1]);
                if(Integer.parseInt(g.replace("$", "")) > i) {
                    i++;
                }            }
        }
        dresponse = dresponse.replace("$*", GeneralUtils.buildMessage(i,args.length,args));
        event.getChannel().send().message(dresponse);


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
