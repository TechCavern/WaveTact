package com.techcavern.wavetact.console;

import com.techcavern.wavetact.annot.CMDLine;
import com.techcavern.wavetact.console.utils.CommandVariables;
import com.techcavern.wavetact.utils.Registry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.objects.CommandLine;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

@CMDLine
public class Help extends CommandLine {

    public Help() {
        super(GeneralUtils.toArray("help"), "Prints this help screen", false);
    }

    @Override
    public void doAction(String[] args, CommandVariables commandVariables) {
        commandVariables.getPrintStream().println("Help:");
        for (CommandLine c : Registry.CommandLines) {
            commandVariables.getPrintStream().println(StringUtils.join(Arrays.asList(c.getArgument()), ", ") + " - " + c.getHelpString());
        }
    }

}
