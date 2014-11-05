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

import java.util.ArrayList;
import java.util.List;

@CMD
@TruCMD
public class Networks extends GenericCommand {

    public Networks() {
        super(GeneralUtils.toArray("networks netlist"), 3, "networks [connected/all/disconnected]", "lists the networks a bot is on");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        String networks = "";
        int netcount = 0;
        List<NetProperty> bufferlist = new ArrayList<>();
        if(args[0].equalsIgnoreCase("connected")){
           for(NetProperty netprop:GeneralRegistry.NetworkName){
               if(netprop.getBot().getState().equals(PircBotX.State.CONNECTED))
                   bufferlist.add(netprop);
           }
        } else if(args[0].equalsIgnoreCase("disconnected")){
            for(NetProperty netprop:GeneralRegistry.NetworkName){
                if(netprop.getBot().getState().equals(PircBotX.State.DISCONNECTED))
                    bufferlist.add(netprop);
            }
        } else {
            for(NetProperty netprop:GeneralRegistry.NetworkName){
               bufferlist.add(netprop);
            }
        }
        for(NetProperty netprop:bufferlist){
            if(netcount > 0){
                networks += ", " + netprop.getProperty();
            }else{
                networks = netprop.getProperty();
            }
            netcount++;
        }
        if(networks.isEmpty())
            IRCUtils.sendMessage(user, channel, "No networks found" +networks , isPrivate);
        else
        IRCUtils.sendMessage(user, channel, netcount + " network(s) found with those network(s) being " +networks , isPrivate);
    }

}
