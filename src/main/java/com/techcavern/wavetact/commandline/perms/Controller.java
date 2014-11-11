package com.techcavern.wavetact.commandline.perms;

import com.techcavern.wavetact.annot.CMDLine;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.database.ControllerUtils;
import com.techcavern.wavetact.utils.objects.CommandLine;

@CMDLine
public class Controller extends CommandLine {

    public Controller() {
        super(GeneralUtils.toArray("controller con"), "controller (-)[user] - adds or removes Controllers", false);
    }

    @Override
    public void doAction(String[] args) {
        if (args[1].startsWith("-")) {
            GeneralRegistry.Controllers.remove(args[1].replace("-", ""));
            ControllerUtils.saveControllers();
        } else {
            GeneralRegistry.Controllers.add(args[1]);
            ControllerUtils.saveControllers();
        }
        System.exit(0);

    }
}

