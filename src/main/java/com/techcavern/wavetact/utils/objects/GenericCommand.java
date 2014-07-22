/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.utils.objects;

import com.techcavern.wavetact.utils.GeneralRegistry;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

/**
 * @author jztech101
 */
public abstract class GenericCommand {

    private final String[] comid;
    private int PermLevel;
    private final String Desc;

    protected GenericCommand(String[] comid, int PermLevel, String Desc) {
        this.comid = comid;
        this.PermLevel = PermLevel;
        this.Desc = Desc;
        GeneralRegistry.AllCommands.add(this);
    }

    public abstract void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception;

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
