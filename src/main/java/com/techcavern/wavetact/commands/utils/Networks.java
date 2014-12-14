package com.techcavern.wavetact.commands.utils;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import com.techcavern.wavetact.utils.objects.NetProperty;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.ArrayList;
import java.util.List;

@CMD
@GenCMD
public class Networks extends GenericCommand {

    public Networks() {
        super(GeneralUtils.toArray("networks netlist"), 0, "networks [connected/all/disconnected]", "lists the networks a network is on", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        String networks = "";
        int netcount = 0;
        List<NetProperty> bufferlist = new ArrayList<>();
        if (args[0].equalsIgnoreCase("connected")) {
            for (NetProperty netprop : GeneralRegistry.NetworkName) {
                if (netprop.getBot().getState().equals(PircBotX.State.CONNECTED))
                    bufferlist.add(netprop);
            }
        } else if (args[0].equalsIgnoreCase("disconnected")) {
            for (NetProperty netprop : GeneralRegistry.NetworkName) {
                if (netprop.getBot().getState().equals(PircBotX.State.DISCONNECTED))
                    bufferlist.add(netprop);
            }
        } else {
            for (NetProperty netprop : GeneralRegistry.NetworkName) {
                bufferlist.add(netprop);
            }
        }
        for (NetProperty netprop : bufferlist) {
            if (netcount > 0) {
                networks += ", " + netprop.getProperty();
            } else {
                networks = netprop.getProperty();
            }
            netcount++;
        }
        if (networks.isEmpty())
            IRCUtils.sendMessage(user, network, channel, "No networks found" + networks, prefix);
        else
            IRCUtils.sendMessage(user, network, channel, netcount + " network(s) found with those network(s) being " + networks, prefix);
    }

}
