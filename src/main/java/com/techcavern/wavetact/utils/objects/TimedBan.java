package com.techcavern.wavetact.utils.objects;


public class TimedBan extends ChannelProperty{
    private final long init;
    private long time;
    private final String hostmask;
    private final boolean isMute;

    public TimedBan(String hostmask, String networkName, String type, String channelName, long time, long init, boolean isMute) {
        super(networkName,channelName, type);
        this.time = time;
        this.init = init;
        this.isMute = isMute;
        this.hostmask = hostmask;
    }


    public long getTime() {
        return this.time;
    }

    public void setTime(long x) {
        this.time = x;
    }

    public boolean isMute(){return isMute;}

    public String getType() {
        return this.getProperty();
    }

    public long getInit() {
        return this.init;
    }

    public String getHostmask(){return this.hostmask; }
}
