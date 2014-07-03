package com.techcavern.wavetact;

import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.commandline.CommandLine;

import org.slf4j.impl.SimpleLogger;

import java.util.concurrent.ForkJoinPool;

@SuppressWarnings("ConstantConditions")
public class Main {


    public static void main(String[] args) throws Exception {
        CommandLine.parseCommandLineArguments(args);
        System.out.println("Starting...");
        System.setProperty(SimpleLogger.SHOW_DATE_TIME_KEY, "true");
        System.setProperty(SimpleLogger.DATE_TIME_FORMAT_KEY, "[yyyy/MM/dd HH:mm:ss]");
        System.setProperty(SimpleLogger.LEVEL_IN_BRACKETS_KEY, "true");
        IRCUtils.registerCommands();
        IRCUtils.registerNetworks();
//        IRCUtils.registerDevServer();
        IRCUtils.loadSimpleActions();
        IRCUtils.loadSimpleMessages();
        IRCUtils.loadBanTimes();
        IRCUtils.loadQuietTimes();
        IRCUtils.startThreads();
        GeneralRegistry.WaveTact.start();
    }
}
