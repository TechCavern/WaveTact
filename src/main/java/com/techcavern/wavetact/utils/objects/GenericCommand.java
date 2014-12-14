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

    private final String[] comID;
    private final String desc;
    private final String Syntax;
    private final boolean channelRequired;

    private int permLevel;

    protected GenericCommand(String[] comID, int permLevel, String syntax, String desc, boolean channelRequired) {
        this.comID = comID;
        this.permLevel = permLevel;
        this.desc = desc;
        this.Syntax = syntax;
        this.channelRequired = channelRequired;
        GeneralRegistry.AllCommands.add(this);
    }

    public abstract void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception;

    public boolean getLockedStatus() {
        return false;
    }

    public int getPermLevel() {
        return permLevel;
    }

    public void setPermLevel(int newPermLevel) {
        this.permLevel = newPermLevel;
    }

    public String[] getCommandID() {
        return comID;
    }

    public String getCommand() {
        return comID[0];
    }

    public boolean getChannelRequired() {
        return channelRequired;
    }

    public String getSyntax() {
        if (Syntax != null) {
            return Syntax;
        } else {
            return "";
        }
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return "Command(" + comID[0] + ")";
    }
}
