/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.dnsinfo;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.TruCMD;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.xbill.DNS.*;

/**
 * @author jztech101
 */
@CMD
@TruCMD
public class DNSBlacklistLookup extends GenericCommand {

    public DNSBlacklistLookup() {
        super(GeneralUtils.toArray("dnsblacklistlookup dbl"), 3, "dnsblacklistlookup [IPv4/Domain/User]", "looks up a domain or IP to see if its in a spam blacklist");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        String BeforeIP = GeneralUtils.getIP(args[0], Bot);
        if (BeforeIP == null) {
            IRCUtils.sendError(user, "Invalid IP/User");
            return;
        } else if (BeforeIP.contains(":")) {
            IRCUtils.sendError(user, "IPv6 is Not supported");
            return;
        }
        String[] IPString = StringUtils.split(BeforeIP, ".");
        String IP = "";
        for (int i = IPString.length - 1; i > -1; i--) {
            if (IP.isEmpty()) {
                IP = IPString[i];
            } else {
                IP += "." + IPString[i];
            }
        }
        Boolean sent = false;
        Resolver resolver = new SimpleResolver();
        if (GeneralRegistry.DNSBLs.isEmpty()) {
            IRCUtils.sendError(user, "No DNS BLs found in Database");
            return;
        }
        for (String Domain : GeneralRegistry.DNSBLs) {
            Lookup lookup = new Lookup(IP + "." + Domain, Type.ANY);
            lookup.setResolver(resolver);
            lookup.setCache(null);
            Record[] records = lookup.run();
            if (lookup.getResult() == lookup.SUCCESSFUL) {
                IRCUtils.SendMessage(user, channel, BeforeIP + " found in " + Domain, isPrivate);
                sent = true;
                for (Record rec : records) {
                    if (rec instanceof TXTRecord) {
                        IRCUtils.SendMessage(user, channel, Type.string(rec.getType()) + " - " + StringUtils.join((TXTRecord) rec, " "), isPrivate);
                    }
                }
            }

        }
        if (!sent) {
            IRCUtils.SendMessage(user, channel, BeforeIP + " not found in DNSBLs", isPrivate);
        }

    }
}

