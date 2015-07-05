package com.techcavern.wavetact.consoleCommands.config;

import com.techcavern.wavetact.annot.ConCMD;
import com.techcavern.wavetact.objects.CommandIO;
import com.techcavern.wavetact.objects.ConsoleCommand;
import com.techcavern.wavetact.utils.DatabaseUtils;
import com.techcavern.wavetact.utils.GeneralUtils;

@ConCMD
public class AddBasicCommands extends ConsoleCommand {

    public AddBasicCommands() {
        super(GeneralUtils.toArray("addbasiccommands"), "addbasiccommands", "Pre-Populate the database with some pre-chosen ircCommands (Use ONCE and only ONCE");
    }

    @Override
    public void onCommand(String command, String[] args, CommandIO commandIO) {
        DatabaseUtils.addCustomCommand(null, null, "potato", 0, "is a potato", true, true);

        DatabaseUtils.addCustomCommand(null, null, "ping", 0, "pong", true, false);
        DatabaseUtils.addCustomCommand(null, null, "pong", 0, "ping", true, false);
        DatabaseUtils.addCustomCommand(null, null, "releases", 0, "https://github.com/TechCavern/WaveTact/releases", true, false);
        DatabaseUtils.addCustomCommand(null, null, "license", 0, "MIT License (https://github.com/TechCavern/WaveTact/blob/master/license.txt)", true, false);
        DatabaseUtils.addCustomCommand(null, null, "source", 0, "http://github.com/TechCavern/WaveTact", true, false);

        commandIO.getPrintStream().println("Basic Commands Added");
    }
}

