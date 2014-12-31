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
    public void onCommand(String[] args, CommandIO commandIO) {
        DatabaseUtils.addCustomCommand(null, null, "potato", 0, "is a potato", true, true);
        DatabaseUtils.addCustomCommand(null, null, "ping", 0, "pong", true, false);
        DatabaseUtils.addCustomCommand(null, null, "pong", 0, "ping", true, false);
        DatabaseUtils.addCustomCommand(null, null, "releases", 0, "https://github.com/TechCavern/WaveTact/releases", true, false);
        DatabaseUtils.addCustomCommand(null, null, "license", 0, "MIT (Do What The Fuck You Want, We take ZERO liability)", true, false);
        DatabaseUtils.addCustomCommand(null, null, "source", 0, "http://github.com/TechCavern/WaveTact", true, false);
        DatabaseUtils.addCustomCommand(null, null, "authors", 0, "Julian (JZTech101)", true, false);
        DatabaseUtils.addCustomCommand(null, null, "contributors", 0, "https://github.com/TechCavern/WaveTact/graphs/contributors", true, false);
        DatabaseUtils.addCustomCommand(null, null, "nom", 0, "noms on $*", true, true);
        commandIO.getPrintStream().println("Basic Commands Added");
    }
}

