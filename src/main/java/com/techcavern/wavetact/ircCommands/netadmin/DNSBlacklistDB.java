/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.ircCommands.netadmin;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.NAdmCMD;
import com.techcavern.wavetact.utils.*;
import com.techcavern.wavetact.utils.databaseUtils.DNSBLUtils;
import com.techcavern.wavetact.utils.objects.IRCCommand;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;


/**
 * @author jztech101
 */
@CMD
@NAdmCMD
public class DNSBlacklistDB extends IRCCommand {

    public DNSBlacklistDB() {
        super(GeneralUtils.toArray("dnsblacklistdb dnsbldb"), 20, "dnsblacklistdb (-)[dns blacklist url]", "Adds/Removes Domains from Spam DNS Blacklists", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {

        if (args.length > 0) {
            if (args[0].startsWith("-")) {
                String Domain = GetUtils.getDNSBLbyDomain(args[0].replaceFirst("-", "")).replaceAll("http://|https://", "");
                if (Domain != null) {
                    Registry.DNSBLs.remove(Domain);
                    DNSBLUtils.saveDNSBLs();
                    IRCUtils.sendMessage(user, network, channel, "DNS blacklist removed", prefix);
                } else {
                    ErrorUtils.sendError(user, "DNS blacklist does not exist on list");
                }
            } else if (args[0].equalsIgnoreCase("list")) {
                if (!Registry.DNSBLs.isEmpty()) {
                    IRCUtils.sendMessage(user, network, channel, StringUtils.join(Registry.DNSBLs, ", "), prefix);
                } else {
                    ErrorUtils.sendError(user, "DNS blacklist is empty");
                }
            } else {
                String Domain = GetUtils.getDNSBLbyDomain(args[0]);
                if (Domain == null) {
                    Registry.DNSBLs.add(args[0]);
                    DNSBLUtils.saveDNSBLs();
                    IRCUtils.sendMessage(user, network, channel, "DNS blacklist added", prefix);
                } else {
                    ErrorUtils.sendError(user, "DNS blacklist is already listed");
                }
            }
        } else {
            ErrorUtils.sendError(user, "Please specify a dns blacklist");
        }
    }
}
