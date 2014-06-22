/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.controller;

import com.techcavern.wavetact.objects.Command;
import com.techcavern.wavetact.utils.IRCUtils;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * @author jztech101
 */
public class Lock extends Command {
    public Lock() {
        super("lock", 9001);
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args)
            throws Exception {
        if (args[0].equalsIgnoreCase("a")) {
            if (args[0].startsWith("-")) {
                IRCUtils.getSimpleAction(args[1].replaceFirst("-", "")).unlock();
            } else {
                IRCUtils.getSimpleAction(args[1].replaceFirst("-", "")).lock();
            }
        } else if (args[0].equalsIgnoreCase("m")) {
            if (args[0].startsWith("-")) {
                IRCUtils.getSimpleMessage(args[1].replaceFirst("-", "")).unlock();
            } else {
                IRCUtils.getSimpleMessage(args[1].replaceFirst("-", "")).lock();
            }
        }
    }
}