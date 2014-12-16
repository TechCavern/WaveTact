package com.techcavern.wavetact.utils.objects;


public class TimedObj extends ChannelProperty{
    private final long init;
    private long time;
    private final String hostmask;

    public TimedObj(String hostmask, String networkName, String type, String channelName, long time, long init) {
        super(networkName,channelName, type);
        this.time = time;
        this.init = init;
        this.hostmask = hostmask;
    }


    public long getTime() {
        return this.time;
    }

    public void setTime(long x) {
        this.time = x;
    }

    public String getType() {
        return this.getProperty();
    }

    public long getInit() {
        return this.init;
    }

    public String getHostmask(){return this.hostmask; }
}
