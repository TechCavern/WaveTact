package com.techcavern.wavetact.utils;

import com.techcavern.wavetact.commandline.Help;
import com.techcavern.wavetact.commandline.utils.AddServer;
import com.techcavern.wavetact.commandline.utils.BasicCommands;
import com.techcavern.wavetact.commandline.utils.Start;
import com.techcavern.wavetact.objects.CommandLine;

public class CommandLineUtils {

    public static void initializeCommandlines() {
        //TODO Fix this to work with @CMDLine once @CMD is fixed
        new AddServer();
        new Help();
        new BasicCommands();
        new Start();
    }

    public static void parseCommandLineArguments(String[] args) {
        boolean exit = false;
        boolean invalid = true;

        if (args.length < 1) {
            Help c = new Help();
            c.doAction(args);
        }

        for (CommandLine c : GeneralRegistry.CommandLineArguments) {
            for (String b : c.getArgument()) {
                for (String s : args) {

                    if (s.equalsIgnoreCase("-" + b)) {
                        c.doAction(args);
                        invalid = false;


                    }
                }
            }
        }
        for (CommandLine c : GeneralRegistry.CommandLines) {
            for (String b : c.getArgument()) {

                if (args[0].equalsIgnoreCase("-" + b)) {
                    c.doAction(args);
                    invalid = false;


                }
            }
        }
        if (invalid) System.exit(0);
    }
}
