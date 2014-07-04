package com.techcavern.wavetact.commandline.utils;

import com.techcavern.wavetact.annot.CMDLine;
import com.techcavern.wavetact.objects.CommandLine;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.LoadUtils;


public class Start extends CommandLine {
    @CMDLine
    public Start() {
        super(GeneralUtils.toArray("start"), "starts the bot", false);
    }

    @Override
    public void doAction(String [] args) {
        if(args.length > 1 && args[1].equalsIgnoreCase("debug")){
            System.out.println("Running in developer mode");
            LoadUtils.registerDevServer();
        }else{
            System.out.println("Running in production mode");
            LoadUtils.registerNetworks();
        }

    }
}

