package com.techcavern.wavetact.commandline;

import com.techcavern.wavetact.annot.CMDLine;
import com.techcavern.wavetact.utils.GeneralRegistry;
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
    public void doAction(String[] args) {
        System.out.println("Help");
        for (CommandLine c : GeneralRegistry.CommandLines) {
            System.out.println("-" + StringUtils.join(Arrays.asList(c.getArgument()), ", ") + " - " + c.getHelpString());
        }
        System.exit(0);
    }

}
