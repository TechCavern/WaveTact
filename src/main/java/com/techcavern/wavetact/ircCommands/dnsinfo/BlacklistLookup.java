/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.ircCommands.dnsinfo;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.DatabaseUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Record;
import org.jooq.Result;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.xbill.DNS.*;

import static com.techcavern.wavetactdb.Tables.BLACKLISTS;

/**
 * @author jztech101
 */
@IRCCMD
public class BlacklistLookup extends IRCCommand {

    public BlacklistLookup() {
        super(GeneralUtils.toArray("blacklistlookup bll"), 1, "blacklistlookup [type] [ip/domain/user]", "Looks up a domain or ip in blacklist database", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        String BeforeIP = GeneralUtils.getIP(args[1], network, false);
        if (BeforeIP == null) {
            IRCUtils.sendError(user, "Invalid ip/user");
            return;
        } else if (BeforeIP.contains(":")) {
            IRCUtils.sendError(user, "IPv6 is not supported");
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
        Result<Record> blacklist = DatabaseUtils.getBlacklists(args[0]);
        if (blacklist.isEmpty()) {
            IRCUtils.sendError(user, "No " + args[0] + " blacklists found in database");
            return;
        }
        for (org.jooq.Record Blacklist : blacklist) {
            Lookup lookup = new Lookup(IP + "." + Blacklist.getValue(BLACKLISTS.URL), Type.ANY);
            lookup.setResolver(resolver);
            lookup.setCache(null);
            org.xbill.DNS.Record[] records = lookup.run();
            if (lookup.getResult() == Lookup.SUCCESSFUL) {
                IRCUtils.sendMessage(user, network, channel, BeforeIP + " found in " + Blacklist.getValue(BLACKLISTS.URL), prefix);
                sent = true;
                for (org.xbill.DNS.Record rec : records) {
                    if (rec instanceof TXTRecord) {
                        IRCUtils.sendMessage(user, network, channel, "[" + Type.string(rec.getType()) + "] " + StringUtils.join(rec, " "), prefix);
                    }
                }
            }
        }
        if (!sent) {
            IRCUtils.sendMessage(user, network, channel, BeforeIP + " not found in " + args[0] + " blacklists", prefix);
        }

    }
}

