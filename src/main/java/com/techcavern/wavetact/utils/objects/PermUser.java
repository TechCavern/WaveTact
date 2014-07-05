package com.techcavern.wavetact.utils.objects;

/**
 * Created by jztech101 on 7/5/14.
 */
public class PermUser {
    private final String PermUser;
    private int PermLevel;

    public PermUser(String permuser, int permlevel){
        this.PermLevel = permlevel;
        this.PermUser = permuser;

    }

    public int getPermLevel(){
        return this.PermLevel;
    }
    public String getPermUser(){
        return this.PermUser;
    }
    public void setPermLevel(int newpermlevel){
        this.PermLevel=newpermlevel;
    }
}
