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
    private String Desc;

    protected Command(String comid, int PermLevel, String Desc) {
        this.comid = comid.toLowerCase();
        this.PermLevel = PermLevel;
        this.Desc = Desc;
        create();

    }

    public Command create() {
        GeneralRegistry.Commands.add(this);
        return this;
    }

    public abstract void onCommand(MessageEvent<?> event, String... args) throws Exception;

    public boolean getLockedStatus() {
        return false;
    }

    public int getPermLevel() {
        return PermLevel;
    }

    public String getCommandID() {
        return comid;
    }
    public String getDesc(){return Desc;}

    @Override
    public String toString() {
        return "Command(" + comid + ")";
    }
}
