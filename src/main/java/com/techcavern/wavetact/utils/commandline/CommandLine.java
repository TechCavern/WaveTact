package com.techcavern.wavetact.utils.commandline;

import com.techcavern.wavetact.utils.Configuration;
import com.techcavern.wavetact.objects.CommandLineObject;

import java.io.File;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class CommandLine
{

    protected static List<CommandLineObject> arguments = new ArrayList<>();

    static
    {
        arguments.add(new Help());
        arguments.add(new AddServer());
    }

    public static void parseCommandLineArguments(String[] args)
    {
        boolean exit = false;
        boolean invalid = true;
        for (CommandLineObject c : arguments)
        {
            for (String s : args)
            {
                if (s.equals("-" + c.getShortArgument()) || s.equals("--" + c.getLongArgument()))
                {
                    c.doAction();
                    invalid = false;
                }
            }
        }
        if (invalid) System.exit(0);
    }

}
