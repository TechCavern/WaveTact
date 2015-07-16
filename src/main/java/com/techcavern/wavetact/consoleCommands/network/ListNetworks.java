package com.techcavern.wavetact.consoleCommands.network;

import com.techcavern.wavetact.annot.ConCMD;
import com.techcavern.wavetact.objects.CommandIO;
import com.techcavern.wavetact.objects.ConsoleCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.Registry;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.PircBotX;

import java.util.ArrayList;
import java.util.List;

@ConCMD
public class ListNetworks extends ConsoleCommand {

    public ListNetworks() {
        super(GeneralUtils.toArray("listnetworks networks"), "listnetworks (connected/disconnected)", "lists all networks");
    }

    @Override
    public void onCommand(String command, String[] args, CommandIO commandIO) throws Exception {
        List<String> networks = new ArrayList<>();
        for (PircBotX net : Registry.networkName.keySet()) {
            if (args.length < 1) {
                networks.add(IRCUtils.getNetworkNameByNetwork(net));
            } else if (args[0].equalsIgnoreCase("connected")) {
                if (net.getState().equals(PircBotX.State.CONNECTED))
                    networks.add(IRCUtils.getNetworkNameByNetwork(net));
            } else if (args[0].equalsIgnoreCase("disconnected")) {
                if (net.getState().equals(PircBotX.State.DISCONNECTED))
                    networks.add(IRCUtils.getNetworkNameByNetwork(net));
            } else {
                networks.add(IRCUtils.getNetworkNameByNetwork(net));
            }
        }
        commandIO.getPrintStream().println(StringUtils.join(networks, ", "));
    }

}
