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

/**
 * @author jztech101
 */
@IRCCMD
public class DNSInfo extends IRCCommand {

    public DNSInfo() {
        super(GeneralUtils.toArray("dnsinfo di dns"), 5, "dns [domain]", "Looks up a domain for information", false);
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
        boolean isSuccessful = false;
        if (lookup.getResult() == Lookup.SUCCESSFUL) {
            for (Record rec : records) {
                if (rec instanceof ARecord) {
                    IRCUtils.sendMessage(user, network, channel, "[" + Type.string(rec.getType()) + "] " + ((ARecord) rec).getAddress().toString().replace("./", "/"), prefix);
                } else if (rec instanceof NSRecord) {
                    IRCUtils.sendMessage(user, network, channel, "[" + Type.string(rec.getType()) + "] " + ((NSRecord) rec).getTarget().toString().replace("./", "/"), prefix);
                } else if (rec instanceof AAAARecord) {
                    IRCUtils.sendMessage(user, network, channel, "[" + Type.string(rec.getType()) + "] " + ((AAAARecord) rec).getAddress().toString().replace("./", "/"), prefix);
                } else if (rec instanceof CNAMERecord) {
                    IRCUtils.sendMessage(user, network, channel, "[" + Type.string(rec.getType()) + "] " + ((CNAMERecord) rec).getAlias() + " - " + ((CNAMERecord) rec).getTarget(), prefix);
                } else if (rec instanceof TXTRecord) {
                    IRCUtils.sendMessage(user, network, channel, "[" + Type.string(rec.getType()) + "] " + StringUtils.join(rec, " "), prefix);
                } else if (rec instanceof MXRecord) {
                    IRCUtils.sendMessage(user, network, channel, "[" + Type.string(rec.getType()) + "] " + ((MXRecord) rec).getPriority() + " - " + ((MXRecord) rec).getTarget(), prefix);
                } else if (rec instanceof SRVRecord) {
                    IRCUtils.sendMessage(user, network, channel, "[" + Type.string(rec.getType()) + "] " + ((SRVRecord) rec).getPriority() + " - " + ((SRVRecord) rec).getTarget() + " - " + ((SRVRecord) rec).getPort(), prefix);
                }
            }
            isSuccessful = true;
        }
        if (!isSuccessful) {
            IRCUtils.sendError(user, network, channel, "Invalid domain", prefix);
        }

    }

}

