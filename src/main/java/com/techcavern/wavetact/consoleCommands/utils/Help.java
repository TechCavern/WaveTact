package com.techcavern.wavetact.consoleCommands.utils;

import com.techcavern.wavetact.annot.ConCMD;
import com.techcavern.wavetact.objects.CommandIO;
import com.techcavern.wavetact.objects.ConsoleCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
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
            ConsoleCommand consolCommand = IRCUtils.getConsoleCommand(args[0]);
            if (consolCommand != null) {
                commandIO.getPrintStream().println("Aliases: " + StringUtils.join(Arrays.asList(consolCommand.getCommandID()), ", "));
                String syntax = consolCommand.getSyntax();
                if (!syntax.isEmpty())
                    commandIO.getPrintStream().println("Syntax: " + syntax);
                commandIO.getPrintStream().println(consolCommand.getDesc());
            } else {
                commandIO.getPrintStream().println("Command does not exist");
            }
        } else {
            commandIO.getPrintStream().println("help (command) - gets help on a specific command");
        }
    }

}
