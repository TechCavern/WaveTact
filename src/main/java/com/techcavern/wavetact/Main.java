package com.techcavern.wavetact;

import com.techcavern.wavetact.utils.CommandLineUtils;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.LoadUtils;

import org.slf4j.impl.SimpleLogger;

@SuppressWarnings("ConstantConditions")
public class Main {


    public static void main(String[] args) throws Exception {
        System.setProperty(SimpleLogger.SHOW_DATE_TIME_KEY, "true");
        System.setProperty(SimpleLogger.DATE_TIME_FORMAT_KEY, "[yyyy/MM/dd HH:mm:ss]");
        System.setProperty(SimpleLogger.LEVEL_IN_BRACKETS_KEY, "true");
        CommandLineUtils.initializeCommandlines();
        CommandLineUtils.parseCommandLineArguments(args);
        LoadUtils.registerCommands();
        LoadUtils.registerDevServer();
        LoadUtils.loadSimpleActions();
        LoadUtils.loadSimpleMessages();
        LoadUtils.startThreads();
        GeneralRegistry.WaveTact.start();
    }
}
