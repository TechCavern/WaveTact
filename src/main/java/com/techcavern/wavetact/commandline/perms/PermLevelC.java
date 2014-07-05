package com.techcavern.wavetact.commandline.perms;

import com.techcavern.wavetact.annot.CMDLine;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.objects.*;
import com.techcavern.wavetact.utils.objects.objectUtils.PermUserUtils;

/**
 * Created by jztech101 on 7/5/14.
 */
public class PermLevelC extends CommandLine {
    @CMDLine
    public PermLevelC() {
        super(GeneralUtils.toArray("controller c"), "controller (-)[user] - adds or removes Controllers", false);
    }

    @Override
    public void doAction(String[] args) {
        if (args[0].startsWith("-")) {
            GeneralRegistry.Controllers.remove(args[1].replace("-", ""));
        } else {
            GeneralRegistry.Controllers.add(args[1]);

        }
    }
}

