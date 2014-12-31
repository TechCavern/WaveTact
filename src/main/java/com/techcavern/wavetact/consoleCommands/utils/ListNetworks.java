package com.techcavern.wavetact.consoleCommands.utils;

import com.techcavern.wavetact.annot.ConCMD;
import com.techcavern.wavetact.objects.CommandIO;
import com.techcavern.wavetact.objects.ConsoleCommand;
import com.techcavern.wavetact.objects.NetProperty;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.Registry;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@ConCMD
public class ListNetworks extends ConsoleCommand {

    public ListNetworks() {
        super(GeneralUtils.toArray("listnetworks networks"),"list", "lists all networks");
    }

    @Override
    public void onCommand(String[] args, CommandIO commandIO) throws Exception{
        List<String> networks = new ArrayList<>();
        for(NetProperty e:Registry.NetworkName){
            networks.add(e.getProperty());
        }
        commandIO.getPrintStream().println(StringUtils.join(networks, ", "));
    }

}
