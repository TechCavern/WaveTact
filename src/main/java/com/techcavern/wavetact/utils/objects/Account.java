package com.techcavern.wavetact.utils.objects;

public class Account {
    private final String AuthAccount;
    private String AuthPassword;

    public Account(String AuthAccount, String AuthPassword) {
        this.AuthAccount = AuthAccount;
        this.AuthPassword = AuthPassword;
    }

    public String getAuthAccount() {
        return this.AuthAccount;
    }

    public String getAuthPassword() {
        return this.AuthPassword;
    }

    public void setAuthPassword(String AuthPassword) {
        this.AuthPassword = AuthPassword;
    }

}
