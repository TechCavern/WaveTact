package com.techcavern.wavetact;

import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.LoadUtils;
import com.techcavern.wavetact.utils.objects.objectUtils.*;
import org.slf4j.impl.SimpleLogger;

@SuppressWarnings("ConstantConditions")
public class Main {


    public static void main(String[] args) throws Exception {
        ControllerUtils.loadControllers();
        LoadUtils.initializeCommandlines();
        LoadUtils.parseCommandLineArguments(args);
        LoadUtils.registerCommands();
        GlobalUtils.loadGlobals();
        BanTimeUtils.loadBanTimes();
        QuietTimeUtils.loadQuietTimes();
        PermChannelUtils.loadPermChannels();
        SimpleActionUtils.loadSimpleActions();
        SimpleMessageUtils.loadSimpleMessages();
        LoadUtils.startThreads();

        GeneralRegistry.WaveTact.start();

    }
}
