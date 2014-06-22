/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.objects;

import com.techcavern.wavetact.utils.GeneralRegistry;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * @author jztech101
 */
public abstract class Command {

    private String comid;
    private int PermLevel;

    protected Command(String comid, int PermLevel) {
        this.comid = comid.toLowerCase();
        this.PermLevel = PermLevel;
        create();

    }

    public Command create() {
        GeneralRegistry.Commands.add(this);
        return this;
    }

    public abstract void onCommand(MessageEvent<?> event, String... args) throws Exception;

    public int getPermLevel() {
        return PermLevel;
    }

    public String getCommandID() {
        return comid;
    }

}
