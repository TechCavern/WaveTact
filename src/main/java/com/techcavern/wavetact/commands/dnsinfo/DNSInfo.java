/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.dnsinfo;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.TruCMD;
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
public class DNSInfo extends GenericCommand {

    public DNSInfo() {
        super(GeneralUtils.toArray("dnsinfo dns"), 3, "dns [domain]", "looks up a domain for information");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
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
                    IRCUtils.SendMessage(user, channel, Type.string(rec.getType()) + " - " + ((ARecord) rec).getAddress(), isPrivate);
                } else if (rec instanceof NSRecord) {
                    IRCUtils.SendMessage(user, channel, Type.string(rec.getType()) + " - " + ((NSRecord) rec).getTarget(), isPrivate);
                } else if (rec instanceof AAAARecord) {
                    IRCUtils.SendMessage(user, channel, Type.string(rec.getType()) + " - " + ((AAAARecord) rec).getAddress(), isPrivate);
                } else if (rec instanceof CNAMERecord) {
                    IRCUtils.SendMessage(user, channel, Type.string(rec.getType()) + " - " + ((CNAMERecord) rec).getAlias() + ((CNAMERecord) rec).getTarget(), isPrivate);
                } else if (rec instanceof TXTRecord) {
                    IRCUtils.SendMessage(user, channel, Type.string(rec.getType()) + " - " + StringUtils.join((TXTRecord) rec, " "), isPrivate);
                } else if (rec instanceof MXRecord) {
                    IRCUtils.SendMessage(user, channel, Type.string(rec.getType()) + " - " + ((MXRecord) rec).getPriority() + ((MXRecord) rec).getTarget(), isPrivate);
                }
            }
            isSuccessful = true;
        }
        if (!isSuccessful) {
            IRCUtils.sendError(user, "Invalid Domain");
        }

    }

}

