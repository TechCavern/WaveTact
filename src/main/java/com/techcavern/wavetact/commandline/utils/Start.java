package com.techcavern.wavetact.commandline.utils;

import com.techcavern.wavetact.annot.CMDLine;
import com.techcavern.wavetact.objects.CommandLine;
import com.techcavern.wavetact.objects.SimpleAction;
import com.techcavern.wavetact.objects.SimpleMessage;
import com.techcavern.wavetact.utils.CommandLineUtils;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;


public class Start extends CommandLine {
    @CMDLine
    public Start() {
        super(GeneralUtils.toArray("start"), "starts the bot", false);
    }

    @Override
    public void doAction(String [] args) {
        if(args[1].equalsIgnoreCase("debug")){
            System.out.println("Running in developer mode");
            IRCUtils.registerDevServer();
        }else{
            System.out.println("Running in production mode");
            IRCUtils.registerNetworks();
        }

    }
}

