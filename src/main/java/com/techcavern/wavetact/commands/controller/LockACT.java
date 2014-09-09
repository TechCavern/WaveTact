/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.controller;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.ConCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.databaseUtils.SimpleActionUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

/**
 * @author jztech101
 */
@CMD
@ConCMD
public class LockACT extends GenericCommand {


    public LockACT() {
        super(GeneralUtils.toArray("lockaction lockact"), 9001, "lockaction [command]", "locks a custom action");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {

        if (args[0].startsWith("-")) {
            SimpleActionUtils.getSimpleAction(args[0].replaceFirst("-", "")).unlock();
            SimpleActionUtils.saveSimpleActions();
            IRCUtils.sendMessage(user, channel, "Simple Action Unlocked", isPrivate);
        } else {
            SimpleActionUtils.getSimpleAction(args[0].replaceFirst("-", "")).lock();
            SimpleActionUtils.saveSimpleActions();
            IRCUtils.sendMessage(user, channel, "Simple Action Unlocked", isPrivate);

        }

    }
}