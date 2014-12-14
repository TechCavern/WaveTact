package com.techcavern.wavetact.commandline.utils;

import com.techcavern.wavetact.annot.CMDLine;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.configurationUtils.NetworkUtils;
import com.techcavern.wavetact.utils.objects.CommandLine;

@CMDLine
public class Start extends CommandLine {

    public Start() {
        super(GeneralUtils.toArray("start"), "starts the network", false);
    }

    @Override
    public void doAction(String[] args) {
        if (args.length >= 2 && (args[1].equalsIgnoreCase("debug") || args[1].toLowerCase().startsWith("dev"))) {
            System.out.println("Running in developer mode");
            NetworkUtils.registerDevServer();
        } else {
            System.out.println("Running in production mode");
            NetworkUtils.registerNetworks();
        }

    }
}

