package com.techcavern.wavetact.consoleCommands.config;

import com.techcavern.wavetact.annot.ConCMD;
import com.techcavern.wavetact.objects.CommandIO;
import com.techcavern.wavetact.objects.ConsoleCommand;
import com.techcavern.wavetact.utils.DatabaseUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.Registry;

@ConCMD
public class Config extends ConsoleCommand {

    public Config() {
        super(GeneralUtils.toArray("config conf"), "config [property] [value]", "config the bot");
    }

    @Override
    public void onCommand(String[] args, CommandIO commandIO) throws Exception {
        switch (args[0].toLowerCase()) {
            case "commandchar":
                DatabaseUtils.removeConfig("commandchar");
                DatabaseUtils.addConfig("commandchar", args[1]);
                success(commandIO);
                break;
            case "wolframalphaapikey":
                DatabaseUtils.removeConfig("wolframalphapikey");
                DatabaseUtils.addConfig("wolframalphapikey", args[1]);
                Registry.wolframalphaapikey = args[1];
                success(commandIO);
                break;
            case "googleapikey":
                DatabaseUtils.removeConfig("googleapikey");
                DatabaseUtils.addConfig("googleapikey", args[1]);
                Registry.googleapikey = args[1];
                success(commandIO);
                break;
            case "wordnikapikey":
                DatabaseUtils.removeConfig("wordnikapikey");
                DatabaseUtils.addConfig("wordnikapikey", args[1]);
                Registry.wordnikapikey = args[1];
                success(commandIO);
                break;
            case "wundergroundapikey":
                DatabaseUtils.removeConfig("wundergroundapikey");
                DatabaseUtils.addConfig("wundergroundapikey", args[1]);
                Registry.googleapikey = args[1];
                success(commandIO);
                break;
            default:
                commandIO.getPrintStream().println("Invalid property");


        }
    }
    private void success(CommandIO commandIO){
        commandIO.getPrintStream().println("Property modified");
    }
}
