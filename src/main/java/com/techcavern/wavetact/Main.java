package com.techcavern.wavetact;

import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.LoadUtils;
import com.techcavern.wavetact.utils.configurationUtils.ConfigUtils;
import com.techcavern.wavetact.utils.runnables.CheckTime;
import com.techcavern.wavetact.utils.databaseUtils.*;
import org.slf4j.impl.SimpleLogger;

@SuppressWarnings("ConstantConditions")
public class Main {


    public static void main(String[] args) throws Exception {
        System.setProperty(SimpleLogger.SHOW_DATE_TIME_KEY, "true");
        System.setProperty(SimpleLogger.DATE_TIME_FORMAT_KEY, "[yyyy/MM/dd HH:mm:ss]");
        System.setProperty(SimpleLogger.LEVEL_IN_BRACKETS_KEY, "true");

        ControllerUtils.loadControllers();
        LoadUtils.initializeCommandlines();
        LoadUtils.parseCommandLineArguments(args);

        AccountUtils.loadAccounts();
        NetAdminUtils.loadNetworkAdmins();
        PermChannelUtils.loadPermChannels();

        ConfigUtils.registerConfigs();
        LoadUtils.registerCommands();
        LoadUtils.registerCommandList();
        SimpleActionUtils.loadSimpleActions();
        SimpleMessageUtils.loadSimpleMessages();

        LoadUtils.registerAttacks();
        LoadUtils.registerEightball();
        IRCBLUtils.loadIRCBLs();
        DNSBLUtils.loadDNSBLs();

        BanTimeUtils.loadBanTimes();
        QuietTimeUtils.loadQuietTimes();
        GeneralRegistry.threadPool.execute(new CheckTime());

        GeneralRegistry.WaveTact.start();
    }
}
