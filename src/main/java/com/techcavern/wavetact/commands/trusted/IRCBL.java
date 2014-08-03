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

import java.util.List;

/**
 * @author jztech101
 */
public class IRCBL extends GenericCommand {

    @CMD
    @TruCMD
    public IRCBL() {
        super(GeneralUtils.toArray("ircbl"), 5, "ircbl [domain]", "looks up a domain for IP to see if its in a blacklist");
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
        if (GeneralRegistry.IRCBLs.isEmpty()) {
            user.send().notice("No IRC BLs found in Database");
            return;
        }
        for (String Domain : GeneralRegistry.IRCBLs) {
            Lookup lookup = new Lookup(IP + "." + Domain, Type.ANY);
            lookup.setResolver(resolver);
            lookup.setCache(null);
            Record[] records = lookup.run();
            if (lookup.getResult() == lookup.SUCCESSFUL) {
                IRCUtils.SendMessage(user, channel, IP + " found in " + Domain, isPrivate);
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

