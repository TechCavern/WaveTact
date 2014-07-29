package com.techcavern.wavetact.utils.objects;

/**
 * Created by jztech101 on 7/5/14.
 */
public class AuthedUser {
    private final String AuthNetwork;
    private final String AuthNick;
    private final String AuthAccount;
    private final String AuthHostmask;

    public AuthedUser(String AuthNetwork, String AuthNick, String AuthAccount, String AuthHostmask) {
        this.AuthAccount = AuthAccount;
        this.AuthNetwork = AuthNetwork;
        this.AuthNick = AuthNick;
        this.AuthHostmask = AuthHostmask;
    }

    public String getAuthAccount(){
        return this.AuthAccount;
    }
    public String getAuthNetwork(){
        return this.AuthNetwork;
    }
    public String getAuthNick(){
        return this.AuthNick;
    }
    public String getAuthHostmask(){
        return this.AuthHostmask;
    }
}
