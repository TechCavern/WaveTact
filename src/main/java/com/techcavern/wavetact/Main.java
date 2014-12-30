package com.techcavern.wavetact;

import com.techcavern.wavetact.utils.consoleUtils.ConsoleClient;
import com.techcavern.wavetact.utils.LoadUtils;
import com.techcavern.wavetact.utils.Registry;
import com.techcavern.wavetact.utils.configurationUtils.ConfigUtils;
import com.techcavern.wavetact.utils.configurationUtils.NetworkUtils;
import com.techcavern.wavetact.utils.databaseUtils.*;
import com.techcavern.wavetact.utils.runnables.BanTimer;
import org.slf4j.impl.SimpleLogger;

import java.util.concurrent.TimeUnit;

@SuppressWarnings("ConstantConditions")
public class Main {

    public static void main(String[] args) throws Exception {
        System.err.println("");
        System.err.println("Welcome to WaveTact!");
        System.err.println("");

        System.setProperty(SimpleLogger.SHOW_DATE_TIME_KEY, "true");
        System.setProperty(SimpleLogger.DATE_TIME_FORMAT_KEY, "[yyyy/MM/dd HH:mm:ss]");
        System.setProperty(SimpleLogger.LEVEL_IN_BRACKETS_KEY, "true");
        LoadUtils.unpackNatives();
        if ((args.length >= 1) && args[0].equalsIgnoreCase("client")){
            ConsoleClient.go();
        }else {
            ControllerUtils.loadControllers();
            NetworkUtils.registerNetworks();
            AccountUtils.loadAccounts();
            NetAdminUtils.loadNetworkAdmins();
            PermChannelUtils.loadPermChannels();
            RelayUtils.loadRelayBots();

            ConfigUtils.registerConfigs();
            LoadUtils.registerCommands();
            LoadUtils.registerCommandList();
            LoadUtils.registerQuiets();
            SimpleActionUtils.loadSimpleActions();
            SimpleMessageUtils.loadSimpleMessages();

            LoadUtils.registerAttacks();
            LoadUtils.registerEightball();
            IRCBLUtils.loadIRCBLs();
            DNSBLUtils.loadDNSBLs();

            BanTimeUtils.loadBanTimes();
            QuietTimeUtils.loadQuietTimes();
            Registry.WaveTact.start();

            Registry.threadPool.execute(Registry.consoleServer);
            Registry.threadPool.execute(new BanTimer());
            Registry.threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        }
    }
}
