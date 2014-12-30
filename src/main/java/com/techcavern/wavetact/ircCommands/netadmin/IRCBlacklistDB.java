/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.ircCommands.netadmin;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.NAdmCMD;
import com.techcavern.wavetact.utils.*;
import com.techcavern.wavetact.utils.databaseUtils.IRCBLUtils;
import com.techcavern.wavetact.objects.IRCCommand;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;


/**
 * @author jztech101
 */
@CMD
@NAdmCMD
public class IRCBlacklistDB extends IRCCommand {


    public IRCBlacklistDB() {
        super(GeneralUtils.toArray("ircblacklistdb ircbldb"), 20, "ircblacklistdb (-)[irc blacklist Url]", "Adds/removes domains from irc blacklist", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        if (args.length > 0) {
            if (args[0].startsWith("-")) {
                String Domain = GetUtils.getIRCDNSBLbyDomain(args[0].replaceFirst("-", "")).replaceAll("http://|https://", "");
                if (Domain != null) {
                    Registry.IRCBLs.remove(Domain);
                    IRCBLUtils.saveIRCBLs();
                    IRCUtils.sendMessage(user, network, channel, "IRC blacklist removed", prefix);
                } else {
                    ErrorUtils.sendError(user, "IRC blacklist does not exist on list");
                }
            } else if (args[0].equalsIgnoreCase("list")) {
                if (!Registry.IRCBLs.isEmpty()) {
                    IRCUtils.sendMessage(user, network, channel, StringUtils.join(Registry.IRCBLs, ", "), prefix);
                } else {
                    ErrorUtils.sendError(user, "IRC blacklist is empty");
                }
            } else {
                String Domain = GetUtils.getIRCDNSBLbyDomain(args[0]);
                if (Domain == null) {
                    Registry.IRCBLs.add(args[0]);
                    IRCBLUtils.saveIRCBLs();
                    IRCUtils.sendMessage(user, network, channel, "IRC blacklist added", prefix);
                } else {
                    ErrorUtils.sendError(user, "IRC blacklist already listed");
                }
            }
        } else {
            ErrorUtils.sendError(user, "Please specify an IRC blacklist");
        }
    }
}
