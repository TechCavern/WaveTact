/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.utils.objects;

import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GeneralUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * @author jztech101
 */
public class SimpleMessage extends Command {

    private final String message;
    private boolean locked;


    public SimpleMessage(String inputString, int permLevel, String message, boolean locked) {
        super(GeneralUtils.toArray(inputString), permLevel, "A Basic Command");
        this.message = message;
        this.locked = locked;
        create();

    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {
        String dresponse = this.message;
        String[] message = StringUtils.split(this.message, " ");
        int i = 0;
        for (String g : message) {
            if (g.startsWith("$") && !g.contains("*")) {
                dresponse = dresponse.replace(g, args[Integer.parseInt(g.replace("$", "")) - 1]);
                if (Integer.parseInt(g.replace("$", "")) > i) {
                    i++;
                }
            }
        }
        dresponse = dresponse.replace("$*", GeneralUtils.buildMessage(i, args.length, args));
        event.getChannel().send().message(dresponse);


    }

    void create(){
        GeneralRegistry.Commands.add(this);
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
