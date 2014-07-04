/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.controller;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.objects.Command;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.utils.SaveUtils;
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
                GetUtils.getSimpleAction(args[1].replaceFirst("-", "")).unlock();
                SaveUtils.saveSimpleActions();
                event.getChannel().send().message("Command Unlocked");
            } else {
                GetUtils.getSimpleAction(args[1].replaceFirst("-", "")).lock();
                SaveUtils.saveSimpleActions();
                event.getChannel().send().message("Command locked");

            }
        } else if (args[0].equalsIgnoreCase("m")) {
            if (args[1].startsWith("-")) {
                GetUtils.getSimpleMessage(args[1].replaceFirst("-", "")).unlock();
                SaveUtils.saveSimpleMessages();
                event.getChannel().send().message("Command Unlocked");

            } else {
                GetUtils.getSimpleMessage(args[1].replaceFirst("-", "")).lock();
                SaveUtils.saveSimpleMessages();
                event.getChannel().send().message("Command locked");

            }
        }
    }
}