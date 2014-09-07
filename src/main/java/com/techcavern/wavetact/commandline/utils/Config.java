package com.techcavern.wavetact.commandline.utils;

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
    public void doAction(String[] args) {
        Scanner input = new Scanner(System.in);
        com.techcavern.wavetact.utils.fileUtils.Configuration config = new com.techcavern.wavetact.utils.fileUtils.Configuration(new File("config.properties"));
        System.out.print("Wolfram API Key: ");
        config.set("wolframapi", input.nextLine());
        System.out.print("Wunderground API Key: ");
        config.set("wundergroundapi", input.nextLine());
        System.out.print("Wordnik API Key: ");
        config.set("wordnikapi", input.nextLine());
        System.out.print("Minecraft API Key (http://theminecraftapi.com/): ");
        config.set("minecraftapi", input.nextLine());
        config.save();
        System.exit(0);
    }
}
