package info.techcavern.wavetact.consoleCommands.utils;

import info.techcavern.wavetact.annot.ConCMD;
import info.techcavern.wavetact.objects.CommandIO;
import info.techcavern.wavetact.objects.ConsoleCommand;
import info.techcavern.wavetact.utils.GeneralUtils;
import info.techcavern.wavetact.utils.IRCUtils;
import info.techcavern.wavetact.annot.ConCMD;
import info.techcavern.wavetact.objects.CommandIO;
import info.techcavern.wavetact.objects.ConsoleCommand;
import info.techcavern.wavetact.utils.GeneralUtils;
import info.techcavern.wavetact.utils.IRCUtils;

@ConCMD
public class Global extends ConsoleCommand {

    public Global() {
        super(GeneralUtils.toArray("global"), "global [message]", "Sends a global to all channels on all networks");
    }

    @Override
    public void onCommand(String command, String[] args, CommandIO commandIO) throws Exception {
        IRCUtils.sendGlobal(GeneralUtils.buildMessage(0, args.length, args), null);
    }
}
