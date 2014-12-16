package com.techcavern.wavetact.utils.objects;


public class ChannelProperty {
    private final String networkName;
    private final String channelName;
    private String property;

    public ChannelProperty(String networkName, String channelName, String property) {
        this.networkName = networkName;
        this.channelName = channelName;
        this.property = property;
    }

    public String getProperty() {return this.property; }

    public void setProperty(String property) {this.property = property; }


    public String getChannelName() {
        return this.channelName;
    }

    public String getNetworkName() {
        return this.networkName;
    }

}
