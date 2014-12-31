/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.ircCommands.dnsinfo;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.annot.TruCMD;
import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.Registry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.objects.IRCCommand;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.xbill.DNS.*;

/**
 * @author jztech101
 */
@IRCCMD
@TruCMD
public class DNSBlacklistLookup extends IRCCommand {

    public DNSBlacklistLookup() {
        super(GeneralUtils.toArray("dnsblacklistlookup dbl"), 5, "dnsblacklistlookup [ip/domain/user]", "Looks up a domain or IP to see if its in a spam blacklist", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        String BeforeIP = GeneralUtils.getIP(args[0], network);
        if (BeforeIP == null) {
            ErrorUtils.sendError(user, "Invalid ip/user");
            return;
        } else if (BeforeIP.contains(":")) {
            ErrorUtils.sendError(user, "IPv6 is not supported");
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
        if (Registry.DNSBLs.isEmpty()) {
            ErrorUtils.sendError(user, "No dns blacklists found in database");
            return;
        }
        for (String Domain : Registry.DNSBLs) {
            Lookup lookup = new Lookup(IP + "." + Domain, Type.ANY);
            lookup.setResolver(resolver);
            lookup.setCache(null);
            Record[] records = lookup.run();
            if (lookup.getResult() == lookup.SUCCESSFUL) {
                IRCUtils.sendMessage(user, network, channel, BeforeIP + " found in " + Domain, prefix);
                sent = true;
                for (Record rec : records) {
                    if (rec instanceof TXTRecord) {
                        IRCUtils.sendMessage(user, network, channel, Type.string(rec.getType()) + " - " + StringUtils.join((TXTRecord) rec, " "), prefix);
                    }
                }
            }

        }
        if (!sent) {
            IRCUtils.sendMessage(user, network, channel, BeforeIP + " not found in dns blacklists", prefix);
        }

    }
}

