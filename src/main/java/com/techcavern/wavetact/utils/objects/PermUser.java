package com.techcavern.wavetact.utils.objects;

import java.util.List;

/**
 * Created by jztech101 on 7/5/14.
 */
public class PermUser {
    private final String PermUser;
    private final List<PermChannel> Channel;
    private final String PermNetwork;
    private final boolean Global;
    public PermUser(String PermNetwork, List<PermChannel> Channel, String permuser, boolean Global){
        this.PermUser = permuser;
        this.Channel=Channel;
        this.Global = Global;
        this.PermNetwork = PermNetwork;
    }
    public String getPermUser(){
        return this.PermUser;
    }
    public boolean getisGlobal() {return this.Global;}
    public String getPermNetwork(){
        return this.PermNetwork;
    }
    public List<PermChannel> getPermChannel(){
        return this.Channel;
    }

}
