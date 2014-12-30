package com.techcavern.wavetact.consoleCommands.utils;

import com.techcavern.wavetact.annot.CMDLine;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.Registry;
import com.techcavern.wavetact.objects.CommandIO;
import com.techcavern.wavetact.objects.ConsoleCommand;
import org.apache.commons.lang3.StringUtils;

@CMDLine
public class ListCommands extends ConsoleCommand {

    public ListCommands() {
        super(GeneralUtils.toArray("listcommands ircCommands list cmds"),"list", "lists all ircCommands");
    }

    @Override
    public void onCommand(String[] args, CommandIO commandIO) {
        commandIO.getPrintStream().println(StringUtils.join(Registry.ConsoleListCommands, ", "));
    }

}
