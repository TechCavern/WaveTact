package com.techcavern.wavetact.consoleCommands.utils;

import com.techcavern.wavetact.annot.ConCMD;
import com.techcavern.wavetact.ircCommands.controller.Connect;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.Registry;
import com.techcavern.wavetact.objects.CommandIO;
import com.techcavern.wavetact.objects.ConsoleCommand;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@ConCMD
public class ListCommands extends ConsoleCommand {

    public ListCommands() {
        super(GeneralUtils.toArray("listcommands ircCommands list cmds"),"list", "lists all ircCommands");
    }

    @Override
    public void onCommand(String[] args, CommandIO commandIO) {
        List<String> commands = new ArrayList<>();
        for(ConsoleCommand cmd:Registry.ConsoleCommands){
            commands.add(cmd.getCommand());
        }
        commandIO.getPrintStream().println(StringUtils.join(commands, ", "));
    }

}
