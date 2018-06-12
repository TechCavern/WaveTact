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
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

@ConCMD
public class Help extends ConsoleCommand {

    public Help() {
        super(GeneralUtils.toArray("help"), "help (command)", "gets help on a specific command");
    }

    @Override
    public void onCommand(String command, String[] args, CommandIO commandIO) throws Exception {
        if (args.length > 0) {
            ConsoleCommand consolCommand = Registry.consoleCommands.get(args[0]);
            if (consolCommand != null) {
                commandIO.getPrintStream().println("Variations: " + StringUtils.join(Arrays.asList(consolCommand.getCommandID()), ", "));
                String syntax = consolCommand.getSyntax();
                if (!syntax.isEmpty())
                    commandIO.getPrintStream().println("Syntax: " + syntax);
                commandIO.getPrintStream().println(consolCommand.getDesc());
            } else {
                commandIO.getPrintStream().println("Command does not exist");
            }
        } else {
            commandIO.getPrintStream().println("help (command) - Run list for available commands, generally a + before something means editing it, and a - means removing it. None means adding it. Time is in [time](s/m/h/d/w) format. [] is a required argument. () is an optional argument.");
        }
    }

}
