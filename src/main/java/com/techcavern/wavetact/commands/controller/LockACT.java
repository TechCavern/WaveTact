/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.controller;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.ConCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.databaseUtils.SimpleActionUtils;
import com.techcavern.wavetact.utils.databaseUtils.SimpleMessageUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

/**
 * @author jztech101
 */
public class LockACT extends GenericCommand {
    @CMD
    @ConCMD

    public LockACT() {
        super(GeneralUtils.toArray("lockaction loact locka loa"), 9001, "lockaction [command]", "locks a custom action");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {

            if (args[1].startsWith("-")) {
                SimpleActionUtils.getSimpleAction(args[1].replaceFirst("-", "")).unlock();
                SimpleActionUtils.saveSimpleActions();
                user.send().notice("Simple Action Unlocked");
            } else {
                SimpleActionUtils.getSimpleAction(args[1].replaceFirst("-", "")).lock();
                SimpleActionUtils.saveSimpleActions();
                user.send().notice("Simple Action locked");

            }

    }
}