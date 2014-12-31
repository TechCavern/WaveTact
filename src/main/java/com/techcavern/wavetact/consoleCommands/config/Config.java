package com.techcavern.wavetact.consoleCommands.config;

import com.techcavern.wavetact.annot.ConCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.objects.CommandIO;
import com.techcavern.wavetact.objects.ConsoleCommand;
import com.techcavern.wavetact.utils.Registry;
import com.techcavern.wavetact.utils.databaseUtils;

import java.io.File;
import java.util.Scanner;

@ConCMD
public class Config extends ConsoleCommand {

    public Config() {
        super(GeneralUtils.toArray("config conf"),"config [property] [value]", "config the bot");
    }

    @Override
    public void onCommand(String[] args, CommandIO commandIO) throws Exception {
        switch(args[0].toLowerCase()){
            case "commandchar":
                databaseUtils.deleteConfig("commandchar");
                databaseUtils.addConfig("commandchar", args[1]);
                break;
            case "wolframalphaapikey":
                databaseUtils.deleteConfig("wolframalphapikey");
                databaseUtils.addConfig("wolframalphapikey", args[1]);
                Registry.wolframalphaapikey = args[1];
                break;
            case "googleapikey":
                databaseUtils.deleteConfig("googleapikey");
                databaseUtils.addConfig("googleapikey", args[1]);
                Registry.googleapikey = args[1];
                break;
            case "wordnikapikey":
                databaseUtils.deleteConfig("wordnikapikey");
                databaseUtils.addConfig("wordnikapikey", args[1]);
                Registry.wordnikapikey = args[1];
                break;
            case "wundergroundapikey":
                databaseUtils.deleteConfig("wundergroundapikey");
                databaseUtils.addConfig("wundergroundapikey", args[1]);
                Registry.googleapikey = args[1];
                break;
            default:
                commandIO.getPrintStream().println("Invalid Property");


        }
    }
}
