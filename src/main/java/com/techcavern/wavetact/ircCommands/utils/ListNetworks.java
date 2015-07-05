package com.techcavern.wavetact.ircCommands.utils;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.objects.NetProperty;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.Registry;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@IRCCMD
public class ListNetworks extends IRCCommand {

    public ListNetworks() {
        super(GeneralUtils.toArray("listnetworks netlist"), 0, "listnetworks [connected/all/disconnected]", "Lists the networks a bot is on", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        String networks = "";
        List<NetProperty> bufferlist = new ArrayList<>();
        if(args.length < 1){
            bufferlist.addAll(Registry.NetworkName.stream().collect(Collectors.toList()));
        } else if (args[0].equalsIgnoreCase("connected")) {
            bufferlist.addAll(Registry.NetworkName.stream().filter(netprop -> netprop.getNetwork().getState().equals(PircBotX.State.CONNECTED)).collect(Collectors.toList()));
        } else if (args[0].equalsIgnoreCase("disconnected")) {
            bufferlist.addAll(Registry.NetworkName.stream().filter(netprop -> netprop.getNetwork().getState().equals(PircBotX.State.DISCONNECTED)).collect(Collectors.toList()));
        }else{
            bufferlist.addAll(Registry.NetworkName.stream().collect(Collectors.toList()));
        }
        int netcount = 0;
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
