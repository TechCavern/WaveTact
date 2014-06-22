/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.utils;

import org.pircbotx.hooks.events.MessageEvent;

/**
 * @author jztech101
 */
public abstract class Command implements ICommand {

    private String comid;
    private int PermLevel;

    protected Command(String comid, int PermLevel) {
        this.comid = comid.toLowerCase();
        this.PermLevel = PermLevel;
        create();

    }

    public ICommand create() {
        GeneralRegistry.Commands.add(this);
        return this;
    }

    public abstract void onCommand(MessageEvent<?> event, String... args) throws Exception;

    @Override
    public int getPermLevel() {
        return PermLevel;
    }

    @Override
    public String getcomid() {
        return comid;
    }
}
