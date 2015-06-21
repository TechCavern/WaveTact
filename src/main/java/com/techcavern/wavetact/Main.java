package com.techcavern.wavetact;

import com.techcavern.wavetact.console.ConsoleClient;
import com.techcavern.wavetact.utils.ConfigUtils;
import com.techcavern.wavetact.utils.LoadUtils;
import com.techcavern.wavetact.utils.Registry;
import org.slf4j.impl.SimpleLogger;

import java.io.File;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("ConstantConditions")
public class Main {

    public static void main(String[] args) throws Exception {
        System.err.println("\nWelcome to WaveTact!\n");

        System.setProperty(SimpleLogger.SHOW_DATE_TIME_KEY, "true");
        System.setProperty(SimpleLogger.DATE_TIME_FORMAT_KEY, "[yyyy/MM/dd HH:mm:ss]");
        System.setProperty(SimpleLogger.LEVEL_IN_BRACKETS_KEY, "true");
        if ((args.length >= 1) && args[0].equalsIgnoreCase("--client")) {
            ConsoleClient.go();
        } else if (new File("./console.unixsocket").exists()) {
            System.err.println("Instance already started");
            System.exit(0);
        } else {
            LoadUtils.initiateDatabaseConnection();
            ConfigUtils.registerNetworks();
            LoadUtils.registerConsoleCommands();
            LoadUtils.registerIRCCommands();
            LoadUtils.registerAttacks();
            LoadUtils.registerEightball();
            LoadUtils.initializeAutoFlushWhoisCache();
            Registry.WaveTact.start();
            Registry.threadPool.execute(Registry.consoleServer);
            LoadUtils.initializeMessageQueue();
            LoadUtils.initalizeBanQueue();
            Registry.threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        }
    }
}
