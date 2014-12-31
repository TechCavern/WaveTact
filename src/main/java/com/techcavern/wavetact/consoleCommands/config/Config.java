package com.techcavern.wavetact.consoleCommands.config;

import com.techcavern.wavetact.annot.ConCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.objects.CommandIO;
import com.techcavern.wavetact.objects.ConsoleCommand;

import java.io.File;
import java.util.Scanner;

@ConCMD
public class Config extends ConsoleCommand {

    public Config() {
        super(GeneralUtils.toArray("config conf"),"config", "pre-config the bot");
    }

    @Override
    public void onCommand(String[] args, CommandIO commandIO) {
        Scanner input = new Scanner(commandIO.getInputStream());
        com.techcavern.wavetact.utils.fileUtils.Configuration config = new com.techcavern.wavetact.utils.fileUtils.Configuration(new File("config.properties"));
        commandIO.getPrintStream().print("Wolfram API Key: ");
        config.set("wolframapi", input.nextLine());
        commandIO.getPrintStream().print("Wunderground API Key: ");
        config.set("wundergroundapi", input.nextLine());
        commandIO.getPrintStream().print("Wordnik API Key: ");
        config.set("wordnikapi", input.nextLine());
        commandIO.getPrintStream().print("Google API Key: ");
        config.set("googleapi", input.nextLine());
        config.save();
        System.exit(0);
    }
}
