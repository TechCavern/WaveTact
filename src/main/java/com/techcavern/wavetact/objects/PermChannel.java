package com.techcavern.wavetact.objects;


public class PermChannel extends ChannelProperty {
    private int PermLevel;

    public PermChannel(String Channel, int permlevel, String PermNetwork, String permuser) {
        super(PermNetwork, Channel, permuser);
        this.PermLevel = permlevel;
    }

    public int getPermLevel() {
        return this.PermLevel;
    }

    public void setPermLevel(int newpermlevel) {
        this.PermLevel = newpermlevel;
    }

    public String getPermUser() {
        return this.getProperty();
    }
}
