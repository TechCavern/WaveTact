package com.techcavern.wavetact.commands.utils;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.annot.TruCMD;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import com.techcavern.wavetact.utils.objects.NetProperty;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@CMD
@TruCMD
public class Networks extends GenericCommand {

    public Networks() {
        super(GeneralUtils.toArray("networks netlist"), 3, "networks", "lists the networks a bot is on");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        String networks = "";
        int netcount = 0;
        for(NetProperty netprop:GeneralRegistry.NetworkName){
            if(netcount > 0){
                networks += ", " + netprop.getProperty();
            }else{
                networks = netprop.getProperty();
            }
            netcount++;
        }
        IRCUtils.sendMessage(user, channel, "I am on " + netcount + " network(s) with those network(s) being " +networks , isPrivate);
    }

}
