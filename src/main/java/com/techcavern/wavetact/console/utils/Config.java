package com.techcavern.wavetact.console.utils;

import com.techcavern.wavetact.annot.CMDLine;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.objects.CommandLine;

import java.io.File;
import java.util.Scanner;

@CMDLine
public class Config extends CommandLine {

    public Config() {
        super(GeneralUtils.toArray("config conf"), "Create a server configuration file", false);
    }

    @Override
    public void doAction(String[] args, CommandVariables commandVariables) {
        Scanner input = new Scanner(commandVariables.getInputStream());
        com.techcavern.wavetact.utils.fileUtils.Configuration config = new com.techcavern.wavetact.utils.fileUtils.Configuration(new File("config.properties"));
        commandVariables.getPrintStream().print("Wolfram API Key: ");
        config.set("wolframapi", input.nextLine());
        commandVariables.getPrintStream().print("Wunderground API Key: ");
        config.set("wundergroundapi", input.nextLine());
        commandVariables.getPrintStream().print("Wordnik API Key: ");
        config.set("wordnikapi", input.nextLine());
        commandVariables.getPrintStream().print("Google API Key: ");
        config.set("googleapi", input.nextLine());
        config.save();
        System.exit(0);
    }
}
