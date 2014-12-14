package com.techcavern.wavetact.commandline.perms;

import com.techcavern.wavetact.annot.CMDLine;
import com.techcavern.wavetact.utils.Constants;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.databaseUtils.ControllerUtils;
import com.techcavern.wavetact.utils.objects.CommandLine;

@CMDLine
public class Controller extends CommandLine {

    public Controller() {
        super(GeneralUtils.toArray("controller con"), "controller (-)[user] - adds or removes Controllers", false);
    }

    @Override
    public void doAction(String[] args) {
        if (args[1].startsWith("-")) {
            Constants.Controllers.remove(args[1].replace("-", ""));
            ControllerUtils.saveControllers();
            System.out.println("removed " + args[1]);
        } else {
            Constants.Controllers.add(args[1]);
            ControllerUtils.saveControllers();
            System.out.println("added " + args[1]);
        }
        System.exit(0);

    }
}

