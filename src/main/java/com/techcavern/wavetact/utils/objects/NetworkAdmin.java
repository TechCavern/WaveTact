package com.techcavern.wavetact.utils.objects;

public class NetworkAdmin {

    private final String Network;
    private final String user;

    public NetworkAdmin(String Network, String user) {
        this.Network = Network;
        this.user = user;

    }


    public String getNetwork() {
        return this.Network;
    }

    public String getUser() {
        return this.user;
    }

}





