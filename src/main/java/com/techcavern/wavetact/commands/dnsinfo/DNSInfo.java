/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.dnsinfo;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.TruCMD;
import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.IRCCommand;
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
public class DNSInfo extends IRCCommand {

    public DNSInfo() {
        super(GeneralUtils.toArray("dnsinfo dns"), 5, "dns [domain]", "Looks up a domain for information", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
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
                    IRCUtils.sendMessage(user, network, channel, Type.string(rec.getType()) + " - " + ((ARecord) rec).getAddress(), prefix);
                } else if (rec instanceof NSRecord) {
                    IRCUtils.sendMessage(user, network, channel, Type.string(rec.getType()) + " - " + ((NSRecord) rec).getTarget(), prefix);
                } else if (rec instanceof AAAARecord) {
                    IRCUtils.sendMessage(user, network, channel, Type.string(rec.getType()) + " - " + ((AAAARecord) rec).getAddress(), prefix);
                } else if (rec instanceof CNAMERecord) {
                    IRCUtils.sendMessage(user, network, channel, Type.string(rec.getType()) + " - " + ((CNAMERecord) rec).getAlias() + ((CNAMERecord) rec).getTarget(), prefix);
                } else if (rec instanceof TXTRecord) {
                    IRCUtils.sendMessage(user, network, channel, Type.string(rec.getType()) + " - " + StringUtils.join((TXTRecord) rec, " "), prefix);
                } else if (rec instanceof MXRecord) {
                    IRCUtils.sendMessage(user, network, channel, Type.string(rec.getType()) + " - " + ((MXRecord) rec).getPriority() + ((MXRecord) rec).getTarget(), prefix);
                }
            }
            isSuccessful = true;
        }
        if (!isSuccessful) {
            ErrorUtils.sendError(user, "Invalid domain");
        }

    }

}

