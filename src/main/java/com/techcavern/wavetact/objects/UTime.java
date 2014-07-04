package com.techcavern.wavetact.objects;


public class UTime {
    private final String hostmask;
    private final String network;
    private final String channel;
    private final String type;
    private long time;

    public UTime(String u, String n, String t, String c, long x) {
        this.time = x;
        this.network = n;
        this.channel = c;
        this.type = t;
        this.hostmask = u;
    }

    public String getHostmask() {
        return this.hostmask;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long x) {
        this.time = x;
    }

    public String getChannel() {
        return this.channel;
    }

    public String getNetwork() {
        return this.network;
    }

    public String getType() {
        return this.type;
    }
}
