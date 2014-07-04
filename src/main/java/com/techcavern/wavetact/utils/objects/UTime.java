package com.techcavern.wavetact.utils.objects;


public class UTime {
    private final String hostmask;
    private final String networkName;
    private final String channelName;
    private final String type;
    private long time;

    public UTime(String hostmask, String networkName, String type, String channelName, long time) {
        this.time = time;
        this.networkName = networkName;
        this.channelName = channelName;
        this.type = type;
        this.hostmask = hostmask;
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

    public String getChannelName() {
        return this.channelName;
    }

    public String getNetworkName() {
        return this.networkName;
    }

    public String getType() {
        return this.type;
    }
}
