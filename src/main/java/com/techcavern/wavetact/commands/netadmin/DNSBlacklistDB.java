/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.netadmin;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.NAdmCMD;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.database.DNSBLUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;


/**
 * @author jztech101
 */
@CMD
@NAdmCMD
public class DNSBlacklistDB extends GenericCommand {

    public DNSBlacklistDB() {
        super(GeneralUtils.toArray("dnsblacklistdb dnsbldb"), 20, "dnsblacklistdb (-)[Spam DNSBL Url]", "Adds/Removes Domains from Spam DNS Blacklists");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {

        if (args.length > 0) {
            if (args[0].startsWith("-")) {
                String Domain = GetUtils.getDNSBLbyDomain(args[0].replaceFirst("-", "")).replaceAll("http://|https://", "");
                if (Domain != null) {
                    GeneralRegistry.DNSBLs.remove(Domain);
                    DNSBLUtils.saveDNSBLs();
                    IRCUtils.sendMessage(user, channel, "Spam DNSBL Removed", isPrivate);
                } else {
                    IRCUtils.sendError(user, "Spam DNSBL does not exist on list");
                }
            } else if (args[0].equalsIgnoreCase("list")) {
                if (!GeneralRegistry.DNSBLs.isEmpty()) {
                    IRCUtils.sendMessage(user, channel, StringUtils.join(GeneralRegistry.DNSBLs, ", "), isPrivate);
                } else {
                    IRCUtils.sendError(user, "Spam DNS Blacklist is Empty");
                }
            } else {
                String Domain = GetUtils.getDNSBLbyDomain(args[0]);
                if (Domain == null) {
                    GeneralRegistry.DNSBLs.add(args[0]);
                    DNSBLUtils.saveDNSBLs();
                    IRCUtils.sendMessage(user, channel, "Spam DNSBL Added", isPrivate);
                } else {
                    IRCUtils.sendError(user, "Spam DNSBL already listed");
                }
            }
        } else {
            IRCUtils.sendError(user, "Please Specifiy an Spam DNBL");
        }
    }
}
