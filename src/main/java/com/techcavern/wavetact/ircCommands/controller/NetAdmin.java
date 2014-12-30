/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.ircCommands.controller;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.ConCMD;
import com.techcavern.wavetact.utils.*;
import com.techcavern.wavetact.utils.databaseUtils.NetAdminUtils;
import com.techcavern.wavetact.utils.objects.IRCCommand;
import com.techcavern.wavetact.utils.objects.NetworkAdmin;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;


/**
 * @author jztech101
 */
@CMD
@ConCMD
public class NetAdmin extends IRCCommand {

    public NetAdmin() {
        super(GeneralUtils.toArray("networkadministrator netadmin"), 9001, "networkadministrator (-)[user]", "Adds a network admin to the network", false);

    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        String networkname = GetUtils.getNetworkNameByNetwork(network);
        String account;
        if (args[0].startsWith("-")) {
            account = args[0].replaceFirst("-", "");
        } else {
            account = args[0];
        }
        String auth = PermUtils.authUser(network, account);
        if (auth != null) {
            account = auth;
        }
        if (account != null) {
            if (args[0].startsWith("-")) {
                if (GetUtils.getNetworkAdminByNick(account, networkname) != null) {
                    Registry.NetworkAdmins.remove(GetUtils.getNetworkAdminByNick(account, networkname));
                    NetAdminUtils.saveNetworkAdmins();
                    IRCUtils.sendMessage(user, network, channel, "Network admin removed", prefix);
                } else {
                    ErrorUtils.sendError(user, "User does not exist in network admin list");
                }
            } else if (args[0].equalsIgnoreCase("list")) {
                String netAdmins = "";
                for (NetworkAdmin netAdmin : Registry.NetworkAdmins) {
                    if (netAdmins.isEmpty()) {
                        netAdmins = netAdmin.getUser();
                    } else {
                        netAdmins += ", " + netAdmin.getUser();
                    }
                }
                if (!netAdmins.isEmpty()) {
                    IRCUtils.sendMessage(user, network, channel, netAdmins, prefix);
                } else {
                    ErrorUtils.sendError(user, "No network admins exist");
                }
            } else {
                if (GetUtils.getNetworkAdminByNick(account, networkname) != null) {
                    ErrorUtils.sendError(user, "User is already in database");
                } else {
                    Registry.NetworkAdmins.add(new NetworkAdmin(networkname, account));
                    NetAdminUtils.saveNetworkAdmins();
                    IRCUtils.sendMessage(user, network, channel, "Network admin added", prefix);
                }
            }
        } else {
            ErrorUtils.sendError(user, "User is not registered with nickserv or not logged in");
        }
    }
}
