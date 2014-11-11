/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.controller;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.ConCMD;
import com.techcavern.wavetact.utils.*;
import com.techcavern.wavetact.utils.database.NetAdminUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import com.techcavern.wavetact.utils.objects.NetworkAdmin;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;


/**
 * @author jztech101
 */
@CMD
@ConCMD
public class NetAdmin extends GenericCommand {

    public NetAdmin() {
        super(GeneralUtils.toArray("networkadministrator netadmin"), 9001, "networkadministrator (-)[user]", "adds a network admin to the bot");

    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {

        String account;
        if (args[0].startsWith("-")) {
            account = args[0].replaceFirst("-", "");
        } else {
            account = args[0];
        }
        String auth = PermUtils.authUser(Bot, account);
        if (auth != null) {
            account = auth;
        }
        if (account != null) {
            if (args[0].startsWith("-")) {
                if (GetUtils.getNetworkAdminByNick(account, Bot.getServerInfo().getNetwork()) != null) {
                    GeneralRegistry.NetworkAdmins.remove(GetUtils.getNetworkAdminByNick(account, Bot.getServerInfo().getNetwork()));
                    NetAdminUtils.saveNetworkAdmins();
                    IRCUtils.sendMessage(user, channel, "Global Removed", isPrivate);
                } else {
                    IRCUtils.sendError(user, "User does not existing in NetworkAdmins");
                }
            } else if (args[0].equalsIgnoreCase("list")) {
                String netAdmins = "";
                for (NetworkAdmin netAdmin : GeneralRegistry.NetworkAdmins) {
                    if (netAdmins.isEmpty()) {
                        netAdmins = netAdmin.getUser();
                    } else {
                        netAdmins += ", " + netAdmin.getUser();
                    }
                }
                if (!netAdmins.isEmpty()) {
                    IRCUtils.sendMessage(user, channel, netAdmins, isPrivate);
                } else {
                    IRCUtils.sendError(user, "No NetworkAdmins Exist");
                }
            } else {
                if (GetUtils.getNetworkAdminByNick(account, Bot.getServerInfo().getNetwork()) != null) {
                    IRCUtils.sendError(user, "User is already in database");
                } else {
                    GeneralRegistry.NetworkAdmins.add(new NetworkAdmin(Bot.getServerInfo().getNetwork(), account));
                    NetAdminUtils.saveNetworkAdmins();
                    IRCUtils.sendMessage(user, channel, "Global Added", isPrivate);
                }
            }
        } else {


            IRCUtils.sendError(user, "User is not registered with Nickserv or not loggedin");
        }
    }
}
