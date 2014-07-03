package com.techcavern.wavetact.utils.commandline;

import com.techcavern.wavetact.objects.CommandLineObject;

public class Help implements CommandLineObject
{

    public String getShortArgument()
    {
        return "h";
    }

    public String getLongArgument()
    {
        return "help";
    }

    public String getHelpString()
    {
        return "Prints this help screen";
    }

    public void doAction()
    {
        System.out.println("Help");
        for (CommandLineObject c : CommandLine.arguments)
        {
            System.out.println("-" + c.getShortArgument() + " or --" + c.getLongArgument() + " - " + c.getHelpString());
        }
        System.exit(0);
    }

}
