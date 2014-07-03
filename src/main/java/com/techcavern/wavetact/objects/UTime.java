package com.techcavern.wavetact.objects;

/**
 * Created by jztech101 on 6/30/14.
 */
public class UTime {
    private long time;
    private final String hostmask;
    private final String network;
    private final String channel;
    private final String type;

    public UTime(String u, String n, String t, String c, long x){
        this.time = x;
        this.network = n;
        this.channel = c;
        this.type = t;
        this.hostmask = u;
    }

    public String getHostmask(){
        return this.hostmask;
    }
    public long getTime(){
        return this.time;
    }
    public String getChannel(){
        return this.channel;
    }
    public String getNetwork(){
        return this.network;
    }
    public String getType(){
        return this.type;
    }
    public void setTime(long x){
        this.time = x;
    }
}
