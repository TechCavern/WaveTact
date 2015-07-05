package com.techcavern.wavetact.consoleCommands.network;

import com.techcavern.wavetact.annot.ConCMD;
import com.techcavern.wavetact.objects.CommandIO;
import com.techcavern.wavetact.objects.ConsoleCommand;
import com.techcavern.wavetact.objects.NetProperty;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.Registry;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@ConCMD
public class ListNetworks extends ConsoleCommand {

    public ListNetworks() {
        super(GeneralUtils.toArray("listnetworks networks"), "listnetworks", "lists all networks");
    }

    @Override
    public void onCommand(String command, String[] args, CommandIO commandIO) throws Exception {
        List<String> networks = Registry.NetworkName.stream().map(NetProperty::getProperty).collect(Collectors.toList());
        commandIO.getPrintStream().println(StringUtils.join(networks, ", "));
    }

}
