package com.techcavern.wavetact.console.perms;

import com.techcavern.wavetact.annot.CMDLine;
import com.techcavern.wavetact.console.utils.CommandVariables;
import com.techcavern.wavetact.utils.Registry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.databaseUtils.ControllerUtils;
import com.techcavern.wavetact.utils.objects.CommandLine;

@CMDLine
public class Controller extends CommandLine {

    public Controller() {
        super(GeneralUtils.toArray("controller con"), "controller (-)[user] - adds or removes Controllers", false);
    }

    @Override
    public void doAction(String[] args, CommandVariables commandVariables) {
        if (args[1].startsWith("-")) {
            Registry.Controllers.remove(args[1].replace("-", ""));
            ControllerUtils.saveControllers();
            commandVariables.getPrintStream().println("removed " + args[1]);
        } else {
            Registry.Controllers.add(args[1]);
            ControllerUtils.saveControllers();
            commandVariables.getPrintStream().println("added " + args[1]);
        }
    }
}

