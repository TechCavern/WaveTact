package com.techcavern.wavetact.consoleCommands.config;

import com.techcavern.wavetact.annot.ConCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.objects.CommandIO;
import com.techcavern.wavetact.objects.ConsoleCommand;
import com.techcavern.wavetact.utils.Registry;
import com.techcavern.wavetact.utils.DatabaseUtils;

@ConCMD
public class Config extends ConsoleCommand {

    public Config() {
        super(GeneralUtils.toArray("config conf"),"config [property] [value]", "config the bot");
    }

    @Override
    public void onCommand(String[] args, CommandIO commandIO) throws Exception {
        switch(args[0].toLowerCase()){
            case "commandchar":
                DatabaseUtils.deleteConfig("commandchar");
                DatabaseUtils.addConfig("commandchar", args[1]);
                break;
            case "wolframalphaapikey":
                DatabaseUtils.deleteConfig("wolframalphapikey");
                DatabaseUtils.addConfig("wolframalphapikey", args[1]);
                Registry.wolframalphaapikey = args[1];
                break;
            case "googleapikey":
                DatabaseUtils.deleteConfig("googleapikey");
                DatabaseUtils.addConfig("googleapikey", args[1]);
                Registry.googleapikey = args[1];
                break;
            case "wordnikapikey":
                DatabaseUtils.deleteConfig("wordnikapikey");
                DatabaseUtils.addConfig("wordnikapikey", args[1]);
                Registry.wordnikapikey = args[1];
                break;
            case "wundergroundapikey":
                DatabaseUtils.deleteConfig("wundergroundapikey");
                DatabaseUtils.addConfig("wundergroundapikey", args[1]);
                Registry.googleapikey = args[1];
                break;
            default:
                commandIO.getPrintStream().println("Invalid Property");


        }
    }
}
