/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.utils;

import org.pircbotx.hooks.events.MessageEvent;

/**
 *
 * @author jztech101
 */
public class SimpleAction extends Command {

    private String action;
    private boolean locked;

    public SimpleAction(String i, int p, String a, boolean b)
    {
        super(i, p);
        this.action = a;
        this.locked = b;
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception{

        event.getChannel().send().action(action);

}public String getAction(){
        return this.action;
    }
 public boolean getLockedStatus(){
     return locked;
 }
}
