package info.techcavern.wavetact.consoleCommands.utils;

import info.techcavern.wavetact.annot.ConCMD;
import info.techcavern.wavetact.objects.CommandIO;
import info.techcavern.wavetact.objects.ConsoleCommand;
import info.techcavern.wavetact.utils.GeneralUtils;
import info.techcavern.wavetact.utils.Registry;
import info.techcavern.wavetact.annot.ConCMD;
import info.techcavern.wavetact.objects.CommandIO;
import info.techcavern.wavetact.objects.ConsoleCommand;
import info.techcavern.wavetact.utils.GeneralUtils;
import info.techcavern.wavetact.utils.Registry;

@ConCMD
public class Shutdown extends ConsoleCommand {

    public Shutdown() {
        super(GeneralUtils.toArray("shutdown stop"), "stop", "Stops the bot.");
    }

    @Override
    public void onCommand(String command, String[] args, CommandIO commandIO) throws Exception {
        Registry.consoleServer.keepConsoleRunning = false;
        Registry.consoleServer.keepConnectionRunning = false;
        commandIO.getPrintStream().println("Shutting down...");
    }
}
