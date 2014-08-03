/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.global;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GloCMD;
import com.techcavern.wavetact.commands.trusted.IRCBL;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.databaseUtils.GlobalUtils;
import com.techcavern.wavetact.utils.databaseUtils.IRCBLUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import com.techcavern.wavetact.utils.objects.Global;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;


/**
 * @author jztech101
 */
public class IRCBLModify extends GenericCommand {
    @CMD
    @GloCMD

    public IRCBLModify() {
        super(GeneralUtils.toArray("ircblm ircblmodify"), 20, "IRCBLModify [IRC DNSBL]","Adds/Removes Domains from IRCBL");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        String Domain = GetUtils.getDNSBLbyDomain(args[0]);

        if (args[0] != null) {
            if (args[0].startsWith("-")) {
                if(Domain != null){
                    GeneralRegistry.IRCBLs.remove(Domain);
                    IRCBLUtils.saveIRCBLs();
                    IRCUtils.SendMessage(user, channel, "IRC DNSBL Removed", isPrivate);
                }else{
                    user.send().notice("IRC DNSBL does not exist on list");
                }
            } else {
                if(Domain == null){
                    GeneralRegistry.IRCBLs.add(args[0]);
                    IRCBLUtils.saveIRCBLs();
                    IRCUtils.SendMessage(user, channel, "IRC DNSBL Added", isPrivate);
                }else{
                    user.send().notice("IRC DNSBL already listed");
                }
            }
        } else {
            user.send().notice("Please Specifiy an IRC DNBL");
        }
    }
}
