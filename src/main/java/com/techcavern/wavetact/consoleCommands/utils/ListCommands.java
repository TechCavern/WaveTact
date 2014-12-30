package com.techcavern.wavetact.consoleCommands.utils;

import com.techcavern.wavetact.annot.CMDLine;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.utils.Registry;
import com.techcavern.wavetact.utils.objects.CommandIO;
import com.techcavern.wavetact.utils.objects.ConsoleCommand;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

@CMDLine
public class ListCommands extends ConsoleCommand {

    public ListCommands() {
        super(GeneralUtils.toArray("listcommands commands list cmds"),"list", "lists all commands");
    }

    @Override
    public void onCommand(String[] args, CommandIO commandIO) {
        commandIO.getPrintStream().println(StringUtils.join(Registry.ConsoleListCommands, ", "));
    }

}
