/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.ircCommands.controller;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.ConCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.olddatabaseUtils.SimpleActionUtils;
import com.techcavern.wavetact.objects.IRCCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

/**
 * @author jztech101
 */
@CMD
@ConCMD
public class LockACT extends IRCCommand {


    public LockACT() {
        super(GeneralUtils.toArray("lockaction lcact lockact"), 9001, "lockaction (-)[command]", "Locks a custom action", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {

        if (args[0].startsWith("-")) {
            SimpleActionUtils.getSimpleAction(args[0].replaceFirst("-", "")).unlock();
            SimpleActionUtils.saveSimpleActions();
            IRCUtils.sendMessage(user, network, channel, "Simple action unlocked", prefix);
        } else {
            SimpleActionUtils.getSimpleAction(args[0].replaceFirst("-", "")).lock();
            SimpleActionUtils.saveSimpleActions();
            IRCUtils.sendMessage(user, network, channel, "Simple action locked", prefix);

        }

    }
}