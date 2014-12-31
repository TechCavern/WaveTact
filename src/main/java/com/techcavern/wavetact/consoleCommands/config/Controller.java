package com.techcavern.wavetact.consoleCommands.config;

import com.techcavern.wavetact.annot.CMDLine;
import com.techcavern.wavetact.objects.CommandIO;
import com.techcavern.wavetact.utils.Registry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.olddatabaseUtils.ControllerUtils;
import com.techcavern.wavetact.objects.ConsoleCommand;

@CMDLine
public class Controller extends ConsoleCommand {

    public Controller() {
        super(GeneralUtils.toArray("controller con"), "controller (-)[user]" ,"adds or removes Controllers");
    }

    @Override
    public void onCommand(String[] args, CommandIO commandIO) {
        if (args[1].startsWith("-")) {
            Registry.Controllers.remove(args[1].replace("-", ""));
            ControllerUtils.saveControllers();
            commandIO.getPrintStream().println("removed " + args[1]);
        } else {
            Registry.Controllers.add(args[1]);
            ControllerUtils.saveControllers();
            commandIO.getPrintStream().println("added " + args[1]);
        }
    }
}

