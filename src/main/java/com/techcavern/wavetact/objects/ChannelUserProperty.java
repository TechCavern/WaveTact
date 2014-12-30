package com.techcavern.wavetact.objects;


public class ChannelUserProperty extends ChannelProperty {
    private String user;

    public ChannelUserProperty(String network,String channel,String user, String property) {
        super(network, channel, property);
        this.user = user;
    }

    public String getUser() {
        return this.user;
    }
}
