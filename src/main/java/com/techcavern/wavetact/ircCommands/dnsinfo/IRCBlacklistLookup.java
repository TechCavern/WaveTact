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
public class IRCBlacklistLookup extends IRCCommand {

    public IRCBlacklistLookup() {
        super(GeneralUtils.toArray("ircblacklistlookup ibl"), 5, "ircblacklistlookup [ip/domain/user]", "Looks up a domain or ip to see if its in a drone blacklist", false);
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
        if (Registry.IRCBLs.isEmpty()) {
            ErrorUtils.sendError(user, "No irc blacklists found in database");
            return;
        }
        for (String Domain : Registry.IRCBLs) {
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
            IRCUtils.sendMessage(user, network, channel, BeforeIP + " not found in irc blacklists", prefix);
        }

    }
}

