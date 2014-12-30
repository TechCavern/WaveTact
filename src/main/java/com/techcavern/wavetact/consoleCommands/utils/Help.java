package com.techcavern.wavetact.consoleCommands.utils;

import com.techcavern.wavetact.annot.CMDLine;
import com.techcavern.wavetact.utils.*;
import com.techcavern.wavetact.utils.objects.CommandIO;
import com.techcavern.wavetact.utils.objects.ConsoleCommand;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

@CMDLine
public class Help extends ConsoleCommand {

    public Help() {
        super(GeneralUtils.toArray("help"),"help (command)", "gets help on a specific command");
    }

    @Override
    public void onCommand(String[] args, CommandIO commandIO) {
        if (args.length > 0) {
            ConsoleCommand command = GetUtils.getConsoleCommand(args[0]);
            if (command != null) {
                commandIO.getPrintStream().println("Aliases: " + StringUtils.join(Arrays.asList(command.getCommandID()), ", "));
                String syntax = command.getSyntax();
                if (!syntax.isEmpty())
                    commandIO.getPrintStream().println("Syntax: " + syntax);
                commandIO.getPrintStream().println(command.getDesc());
            } else {
                commandIO.getPrintStream().println("Command does not exist");
            }
        } else {
            commandIO.getPrintStream().println("help (command) - gets help on a specific command");
        }
    }

}
