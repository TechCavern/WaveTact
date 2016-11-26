package com.techcavern.wavetact;

import com.techcavern.wavetact.console.ConsoleClient;
import com.techcavern.wavetact.eventListeners.MCStatusListener;
import com.techcavern.wavetact.utils.*;
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
        if(args.length == 1 && args[0].equalsIgnoreCase("--windowsdev")){
            load(true);
        }else{
            if ((args.length >= 1) && args[0].equalsIgnoreCase("--client")) {
                ConsoleClient.go();
            } else if (new File("./console.unixsocket").exists()) {
                System.err.println("Instance already started");
                System.exit(0);
            } else {
                load(false);
            }
        }
    }
    public static void load(boolean isWindows) throws Exception{
        LoadUtils.initiateDatabaseConnection();
        LoadUtils.migrate();
        ConfigUtils.registerNetworks();
        LoadUtils.registerConsoleCommands();
        LoadUtils.registerIRCCommands();
        LoadUtils.registerCharReplacements();
        LoadUtils.registerAttacks();
        LoadUtils.registerEightball();
        LoadUtils.initializeAutoFlushWhoisCache();
        Registry.threadPool.execute(new MCStatusListener());
        /**
      DatabaseUtils.addNetwork("Freenode", 6697, "irc.freenode.net", "WTTest", "#tctest", null, false, "JZTech101", "nickserv", , null, "nickserv", true);
        DatabaseUtils.addNetwork("Freenode3", 6697, "irc.freenode.net", "WTTest2", "#tctest", null, false, "JZTech101", "nickserv", null, null, null, true);
        DatabaseUtils.addNetwork("Freenode2", 6697, "irc.freenode.net", "WTTest3", "#tctest", null, false, "JZTech101", "nickserv", null, null, null, true);
         **/

        if(!isWindows)
            Registry.threadPool.execute(Registry.consoleServer);
        Registry.WaveTact.start();
        LoadUtils.initializeMessageQueue();
        LoadUtils.initializeBanQueue();
        LoadUtils.initializeVoiceQueue();
        Registry.threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
    }
}
