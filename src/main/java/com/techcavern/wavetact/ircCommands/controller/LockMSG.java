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
import com.techcavern.wavetact.utils.olddatabaseUtils.SimpleMessageUtils;
import com.techcavern.wavetact.objects.IRCCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

/**
 * @author jztech101
 */
@CMD
@ConCMD
public class LockMSG extends IRCCommand {

    public LockMSG() {
        super(GeneralUtils.toArray("lockmessage lcmsg lockmsg"), 9001, "lockmessage (-)[command]", "Locks a custom message", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {

        if (args[0].startsWith("-")) {
            SimpleMessageUtils.getSimpleMessage(args[0].replaceFirst("-", "")).unlock();
            SimpleMessageUtils.saveSimpleMessages();
            IRCUtils.sendMessage(user, network, channel, "Simple message unlocked", prefix);

        } else {
            SimpleMessageUtils.getSimpleMessage(args[0]).lock();
            SimpleMessageUtils.saveSimpleMessages();
            IRCUtils.sendMessage(user, network, channel, "Simple message locked", prefix);

        }
    }
}
