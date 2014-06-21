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
public interface ICommand {
    public ICommand create();
    public void onCommand(MessageEvent<?> event, String... args) throws Exception;
    public int getPermLevel();
    public String getcomid();
}
