/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.controller;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.utils.objects.Command;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.objects.objectUtils.SimpleActionUtils;
import com.techcavern.wavetact.utils.objects.objectUtils.SimpleMessageUtils;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * @author jztech101
 */
public class Lock extends Command {
    @CMD
    public Lock() {
        super(GeneralUtils.toArray("lock lo"), 9001, "lock [type(m/a)] [command]");
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args)
            throws Exception {
        if (args[0].equalsIgnoreCase("a")) {
            if (args[1].startsWith("-")) {
                SimpleActionUtils.getSimpleAction(args[1].replaceFirst("-", "")).unlock();
                SimpleActionUtils.saveSimpleActions();
                event.getChannel().send().message("Command Unlocked");
            } else {
                SimpleActionUtils.getSimpleAction(args[1].replaceFirst("-", "")).lock();
                SimpleActionUtils.saveSimpleActions();
                event.getChannel().send().message("Command locked");

            }
        } else if (args[0].equalsIgnoreCase("m")) {
            if (args[1].startsWith("-")) {
                SimpleMessageUtils.getSimpleMessage(args[1].replaceFirst("-", "")).unlock();
                SimpleMessageUtils.saveSimpleMessages();
                event.getChannel().send().message("Command Unlocked");

            } else {
                SimpleMessageUtils.getSimpleMessage(args[1].replaceFirst("-", "")).lock();
                SimpleMessageUtils.saveSimpleMessages();
                event.getChannel().send().message("Command locked");

            }
        }
    }
}