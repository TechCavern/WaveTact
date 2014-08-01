/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.controller;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.ConCMD;
import com.techcavern.wavetact.utils.*;
import com.techcavern.wavetact.utils.databaseUtils.GlobalUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import com.techcavern.wavetact.utils.objects.Global;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;


/**
 * @author jztech101
 */
public class Globals extends GenericCommand {
    @CMD
    @ConCMD
    public Globals() {
        super(GeneralUtils.toArray("globals global gl"), 9001, "global (-)[user]", "adds a network admin to the bot");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {

        String account;
        if (args[0].startsWith("-")) {
            account = PermUtils.getAccount(Bot, args[0].replace("-", ""));
        } else {
            account = PermUtils.getAccount(Bot, args[0]);

        }
        if (account != null) {
            if (args[0].startsWith("-")) {
                if (GetUtils.getGlobalByNick(account, Bot.getServerInfo().getNetwork()) != null) {
                    GeneralRegistry.Globals.remove(GetUtils.getGlobalByNick(account, Bot.getServerInfo().getNetwork()));
                    GlobalUtils.saveGlobals();
                    IRCUtils.SendMessage(user, channel, "Global Removed", isPrivate);
                } else {
                    user.send().notice("User does not existing in Globals");
                }
            } else {
                if (GetUtils.getGlobalByNick(account, Bot.getServerInfo().getNetwork()) != null) {
                    user.send().notice("User is already in database");
                } else {
                    GeneralRegistry.Globals.add(new Global(Bot.getServerInfo().getNetwork(), account));
                    GlobalUtils.saveGlobals();
                    IRCUtils.SendMessage(user, channel, "Global Added", isPrivate);
                }
            }
        } else {


            user.send().notice("User is not registered with Nickserv or not loggedin");
        }
    }
}
