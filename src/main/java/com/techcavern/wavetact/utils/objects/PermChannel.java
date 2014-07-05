package com.techcavern.wavetact.utils.objects;

/**
 * Created by jztech101 on 7/5/14.
 */
public class PermChannel {
    private final String Channel;
    private int PermLevel;
    private boolean auto;

    public PermChannel(String Channel, int permlevel, boolean auto){
        this.PermLevel = permlevel;
        this.Channel = Channel;
        this.auto=auto;
    }

    public int getPermLevel(){
        return this.PermLevel;
    }
    public String getChannel() {return this.Channel; }
    public boolean getAuto(){return this.auto;}
    public void setPermLevel(int newpermlevel){
        this.PermLevel=newpermlevel;
    }
    public void setAuto(boolean auto){this.auto = auto;}
}
