/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.utils.objects;

import com.techcavern.wavetact.utils.GeneralRegistry;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * @author jztech101
 */
public abstract class Command {

    private final String[] comid;
    private int PermLevel;
    private final String Desc;

    protected Command(String[] comid, int PermLevel, String Desc) {
        this.comid = comid;
        this.PermLevel = PermLevel;
        this.Desc = Desc;
    }

    public abstract void onCommand(MessageEvent<?> event, String... args) throws Exception;

    public boolean getLockedStatus() {
        return false;
    }

    public int getPermLevel() {
        return PermLevel;
    }

    public String[] getCommandID() {
        return comid;
    }

    public void setPermLevel(int newpermlevel) {
        this.PermLevel = newpermlevel;
    }

    public String getCommand() {
        return comid[0];
    }

    public String getDesc() {
        return Desc;
    }

    @Override
    public String toString() {
        return "Command(" + comid[0] + ")";
    }
}
