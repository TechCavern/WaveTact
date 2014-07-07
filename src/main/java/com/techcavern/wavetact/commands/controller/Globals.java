/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.controller;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.utils.PermUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import com.techcavern.wavetact.utils.databaseUtils.GlobalUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;


/**
 * @author jztech101
 */
public class Globals extends GenericCommand {
    @CMD
    public Globals() {
        super(GeneralUtils.toArray("globals global gl"), 9001, "global (-)[user]");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, String... args)
            throws Exception {
        if (args[0].startsWith("-")) {
            GeneralRegistry.Globals.remove(PermUtils.getAccount(Bot, GetUtils.getUserByNick(channel, args[0].replace("-", ""))));
            GlobalUtils.saveGlobals();
            user.send().notice("Global Removed");
        } else {
            GeneralRegistry.Globals.add(PermUtils.getAccount(Bot, GetUtils.getUserByNick(channel, args[0].replace("-", ""))));
            GlobalUtils.saveGlobals();
            user.send().notice("Global Added");
        }
    }
}
