package com.techcavern.wavetact;

import com.techcavern.wavetact.utils.CommandLineUtils;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.IRCUtils;

import org.slf4j.impl.SimpleLogger;

@SuppressWarnings("ConstantConditions")
public class Main {


    public static void main(String[] args) throws Exception {
        if (!Boolean.parseBoolean(System.getProperty("dev"))) {
            System.out.println("Running in production mode");
            CommandLineUtils.initializeCommandlines();
            CommandLineUtils.parseCommandLineArguments(args);
            IRCUtils.registerNetworks();
        } else {
            System.out.println("Running in developer mode");
            IRCUtils.registerDevServer();
        }

        System.setProperty(SimpleLogger.SHOW_DATE_TIME_KEY, "true");
        System.setProperty(SimpleLogger.DATE_TIME_FORMAT_KEY, "[yyyy/MM/dd HH:mm:ss]");
        System.setProperty(SimpleLogger.LEVEL_IN_BRACKETS_KEY, "true");
        IRCUtils.registerCommands();
        IRCUtils.registerDevServer();
        IRCUtils.loadSimpleActions();
        IRCUtils.loadSimpleMessages();
        IRCUtils.startThreads();
        GeneralRegistry.WaveTact.start();
    }
}
