package com.techcavern.wavetact.consoleCommands.config;

import com.techcavern.wavetact.annot.ConCMD;
import com.techcavern.wavetact.objects.CommandIO;
import com.techcavern.wavetact.objects.ConsoleCommand;
import com.techcavern.wavetact.utils.DatabaseUtils;
import com.techcavern.wavetact.utils.GeneralUtils;

@ConCMD
public class Config extends ConsoleCommand {

    public Config() {
        super(GeneralUtils.toArray("config conf"), "config [property] [value]", "config the bot");
    }

    @Override
    public void onCommand(String[] args, CommandIO commandIO) throws Exception {
        if (args.length < 2) {
            String value = DatabaseUtils.getConfig(args[0]);
            if (value != null) {
                commandIO.getPrintStream().println(value);
            } else {
                commandIO.getPrintStream().println("Property not found");
            }
        } else {
            DatabaseUtils.removeConfig(args[0]);
            DatabaseUtils.addConfig(args[0], args[1]);
            commandIO.getPrintStream().println("Property modified");
        }
    }
}
