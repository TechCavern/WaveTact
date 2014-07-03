package com.techcavern.wavetact.commandline;

import org.apache.commons.lang3.StringUtils;
import com.techcavern.wavetact.objects.CommandLine;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GeneralUtils;

import java.util.Arrays;

public class Help extends CommandLine
{
    public Help(){
        super(GeneralUtils.toArray("h help"), "Prints this help screen");
    }

    @Override
    public void doAction(String [] args)
    {

        System.out.println("Help");
        for (CommandLine c : GeneralRegistry.CommandLines)
        {
            System.out.println("-" + StringUtils.join(Arrays.asList(c.getArgument()), ", ")+ c.getHelpString());
        }
        System.exit(0);
    }

}
