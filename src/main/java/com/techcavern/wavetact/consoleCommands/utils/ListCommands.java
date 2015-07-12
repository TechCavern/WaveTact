package com.techcavern.wavetact.consoleCommands.utils;

import com.techcavern.wavetact.annot.ConCMD;
import com.techcavern.wavetact.objects.CommandIO;
import com.techcavern.wavetact.objects.ConsoleCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.Registry;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ConCMD
public class ListCommands extends ConsoleCommand {

    public ListCommands() {
        super(GeneralUtils.toArray("listcommands commands list cmds"), "list", "lists all ircCommands");
    }

    @Override
    public void onCommand(String command, String[] args, CommandIO commandIO) throws Exception {
        List<String> commands = Registry.ConsoleCommands.stream().map(ConsoleCommand::getCommand).collect(Collectors.toList());
        Collections.sort(commands);
        commandIO.getPrintStream().println(StringUtils.join(commands, ", "));
    }

}
