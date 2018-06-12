package info.techcavern.wavetact.consoleCommands.network;

import info.techcavern.wavetact.annot.ConCMD;
import info.techcavern.wavetact.objects.CommandIO;
import info.techcavern.wavetact.objects.ConsoleCommand;
import info.techcavern.wavetact.utils.GeneralUtils;
import info.techcavern.wavetact.utils.IRCUtils;
import info.techcavern.wavetact.utils.Registry;
import info.techcavern.wavetact.annot.ConCMD;
import info.techcavern.wavetact.objects.CommandIO;
import info.techcavern.wavetact.objects.ConsoleCommand;
import info.techcavern.wavetact.utils.GeneralUtils;
import info.techcavern.wavetact.utils.IRCUtils;
import info.techcavern.wavetact.utils.Registry;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.PircBotX;

import java.util.HashSet;
import java.util.Set;

@ConCMD
public class ListNetworks extends ConsoleCommand {

    public ListNetworks() {
        super(GeneralUtils.toArray("listnetworks networks"), "listnetworks (connected/disconnected)", "lists all networks");
    }

    @Override
    public void onCommand(String command, String[] args, CommandIO commandIO) throws Exception {
        Set<String> networks = new HashSet<>();
        for (PircBotX net : Registry.networks.inverse().keySet()) {
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
