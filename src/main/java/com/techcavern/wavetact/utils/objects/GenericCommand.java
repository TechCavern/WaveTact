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
    private final String Desc;
    private final String Syntax;

    private int PermLevel;

    protected GenericCommand(String[] comid, int PermLevel, String Syntax, String Desc) {
        this.comid = comid;
        this.PermLevel = PermLevel;
        this.Desc = Desc;
        this.Syntax = Syntax;
        GeneralRegistry.AllCommands.add(this);
    }

    public abstract void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception;

    public boolean getLockedStatus() {
        return false;
    }

    public int getPermLevel() {
        return PermLevel;
    }

    public void setPermLevel(int newpermlevel) {
        this.PermLevel = newpermlevel;
    }

    public String[] getCommandID() {
        return comid;
    }

    public String getCommand() {
        return comid[0];
    }

    public String getSyntax() {
        if(Syntax != null) {
            return Syntax;
        }else{
            return "";
        }
    }

    public String getDesc() {
        return Desc;
    }

    @Override
    public String toString() {
        return "Command(" + comid[0] + ")";
    }
}
