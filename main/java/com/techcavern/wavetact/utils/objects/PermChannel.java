package com.techcavern.wavetact.utils.objects;

/**
 * Created by jztech101 on 7/5/14.
 */
public class PermChannel {
    private final String Channel;
    private final String PermNetwork;
    private final String permuser;
    private int PermLevel;
    private boolean auto;

    public PermChannel(String Channel, int permlevel, boolean auto, String PermNetwork, String permuser) {
        this.PermLevel = permlevel;
        this.Channel = Channel;
        this.auto = auto;
        this.permuser = permuser;
        this.PermNetwork = PermNetwork;
    }

    public int getPermLevel() {
        return this.PermLevel;
    }

    public void setPermLevel(int newpermlevel) {
        this.PermLevel = newpermlevel;
    }

    public String getChannel() {
        return this.Channel;
    }

    public boolean getAuto() {
        return this.auto;
    }

    public void setAuto(boolean auto) {
        this.auto = auto;
    }

    public String getPermUser() {
        return this.permuser;
    }

    public String getPermNetwork() {
        return this.PermNetwork;
    }
}
