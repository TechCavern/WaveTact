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
public class IRCBlacklistLookup extends GenericCommand {

    public IRCBlacklistLookup() {
        super(GeneralUtils.toArray("ircblacklistlookup ibl"), 5, "ircblacklistlookup [IPv4/Domain/User]", "looks up a domain or IP to see if its in a drone blacklist", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        String BeforeIP = GeneralUtils.getIP(args[0], network);
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
        if (GeneralRegistry.IRCBLs.isEmpty()) {
            IRCUtils.sendError(user, "No IRC BLs found in Database");
            return;
        }
        for (String Domain : GeneralRegistry.IRCBLs) {
            Lookup lookup = new Lookup(IP + "." + Domain, Type.ANY);
            lookup.setResolver(resolver);
            lookup.setCache(null);
            Record[] records = lookup.run();
            if (lookup.getResult() == Lookup.SUCCESSFUL) {
                IRCUtils.sendMessage(user, network, channel, BeforeIP + " found in " + Domain, prefix);
                sent = true;
                for (Record rec : records) {
                    if (rec instanceof TXTRecord) {
                        TXTRecord c = (TXTRecord) rec;
                        IRCUtils.sendMessage(user, network, channel, Type.string(rec.getType()) + " - " + StringUtils.join((TXTRecord) rec, " "), prefix);
                    }
                }
            }

        }
        if (!sent) {
            IRCUtils.sendMessage(user, network, channel, BeforeIP + " not found in IRC BLs", prefix);
        }

    }
}

