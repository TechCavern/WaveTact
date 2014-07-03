package com.techcavern.wavetact;

import com.techcavern.wavetact.thread.CheckTime;
import com.techcavern.wavetact.utils.Configuration;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.IRCUtils;
import org.pircbotx.PircBotX;
import org.slf4j.impl.SimpleLogger;

import java.io.File;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.Map;
import java.util.HashMap;

@SuppressWarnings("ConstantConditions")
class Main {


    public static void main(String[] args) throws Exception {
        System.out.println("Starting...");
        System.setProperty(SimpleLogger.SHOW_DATE_TIME_KEY, "true");
        System.setProperty(SimpleLogger.DATE_TIME_FORMAT_KEY, "[yyyy/MM/dd HH:mm:ss]");
        System.setProperty(SimpleLogger.LEVEL_IN_BRACKETS_KEY, "true");
        IRCUtils.registerCommands();
        IRCUtils.registerNetworks(args[0]);
//        IRCUtils.registerDevServer();
        IRCUtils.loadSimpleActions();
        IRCUtils.loadSimpleMessages();
        IRCUtils.loadBanTimes();
        IRCUtils.loadQuietTimes();
        IRCUtils.startThreads();
        GeneralRegistry.WaveTact.start();
    }
}
