/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.ircCommands.dnsinfo;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.xbill.DNS.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author jztech101
 */
@IRCCMD
public class DNSInfo extends IRCCommand {

    public DNSInfo() {
        super(GeneralUtils.toArray("dnsinfo di dns"), 1, "dnsinfo [domain]", "Looks up a domain for information", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        Resolver resolver = new SimpleResolver();
        String domain = args[0];
        domain = domain.replace("http://", "").replace("https://", "");
        Lookup lookup = new Lookup(domain, Type.ANY);
        lookup.setResolver(resolver);
        lookup.setCache(null);
        Record[] records = lookup.run();
        if (lookup.getResult() == Lookup.SUCCESSFUL) {
            List<String> results = new ArrayList<>();
            for (Record rec : records) {
                if (rec instanceof ARecord) {
                    results.add("[" + Type.string(rec.getType()) + "] " + ((ARecord) rec).getAddress().toString().replace("./", "/"));
                } else if (rec instanceof NSRecord) {
                    results.add("[" + Type.string(rec.getType()) + "] " + ((NSRecord) rec).getTarget().toString().replace("./", "/"));
                } else if (rec instanceof AAAARecord) {
                    results.add( "[" + Type.string(rec.getType()) + "] " + ((AAAARecord) rec).getAddress().toString().replace("./", "/"));
                } else if (rec instanceof CNAMERecord) {
                    results.add("[" + Type.string(rec.getType()) + "] " + ((CNAMERecord) rec).getAlias() + " - " + ((CNAMERecord) rec).getTarget());
                } else if (rec instanceof TXTRecord) {
                    results.add( "[" + Type.string(rec.getType()) + "] " + StringUtils.join(rec, " "));
                } else if (rec instanceof MXRecord) {
                    results.add("[" + Type.string(rec.getType()) + "] " + ((MXRecord) rec).getPriority() + " - " + ((MXRecord) rec).getTarget());
                } else if (rec instanceof SRVRecord) {
                }
            }
            IRCUtils.sendMessage(user, network, channel,StringUtils.join(results, " - "), prefix);
        }else{
            IRCUtils.sendError(user, network, channel, "Invalid domain", prefix);
        }

    }

}

