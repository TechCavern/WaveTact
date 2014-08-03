/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.trusted;

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
public class DNSBL extends GenericCommand {

    @CMD
    @TruCMD
    public DNSBL() {
        super(GeneralUtils.toArray("dnsbl"), 5, "dnsbl [domain]", "looks up a domain or IP to see if its in a spam blacklist");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        String BeforeIP = "";
        if (args[0].contains(".") || args[0].contains(":")) {
            BeforeIP = args[0];
        } else {
            BeforeIP = IRCUtils.getHost(Bot, args[0]);
        }
        if(BeforeIP.replaceFirst(":", "").contains(":")){
            user.send().notice("IPv6 is not supported");
            return;
        }
        if(BeforeIP.isEmpty()){
            user.send().notice("argument #1 should be the IP");
            return;
        }
        String[] IPString = StringUtils.split(BeforeIP, ".");
        String IP = "";
        for(int i = IPString.length-1; i > -1; i--){
            if(IP.isEmpty()){
                IP = IPString[i];
            }else{
                IP += "." + IPString[i];
            }
        }
        Boolean sent = false;
        Resolver resolver = new SimpleResolver();
        if (GeneralRegistry.DNSBLs.isEmpty()) {
            user.send().notice("No DNS BLs found in Database");
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
                        TXTRecord c = (TXTRecord) rec;
                        IRCUtils.SendMessage(user, channel, Type.string(rec.getType()) + " - " + StringUtils.join((TXTRecord) rec, ""), isPrivate);
                    } else if (rec instanceof ARecord) {
                        ARecord c = (ARecord) rec;
                        IRCUtils.SendMessage(user, channel, Type.string(rec.getType()) + " - " + ((ARecord) rec).getAddress(), isPrivate);
                    } else if (rec instanceof AAAARecord) {
                        AAAARecord c = (AAAARecord) rec;
                        IRCUtils.SendMessage(user, channel, Type.string(rec.getType()) + " - " + ((AAAARecord) rec).getAddress(), isPrivate);
                    }
                }
            }

        }
        if(!sent){
            IRCUtils.SendMessage(user, channel, args[0] + " not found in DNSBLs", isPrivate);
        }

    }
}

