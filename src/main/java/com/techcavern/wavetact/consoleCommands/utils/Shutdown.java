package com.techcavern.wavetact.consoleCommands.utils;

import com.techcavern.wavetact.annot.ConCMD;
import com.techcavern.wavetact.objects.CommandIO;
import com.techcavern.wavetact.objects.ConsoleCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.Registry;

@ConCMD
public class Shutdown extends ConsoleCommand {

    public Shutdown() {
        super(GeneralUtils.toArray("shutdown stop"), "stop", "Stops the bot.");
    }

    @Override
    public void onCommand(String[] args, CommandIO commandIO) throws Exception {
        Registry.consoleServer.keepConsoleRunning = false;
        Registry.consoleServer.keepConnectionRunning = false;
        commandIO.getPrintStream().println("Shutting down...");
    }
}
