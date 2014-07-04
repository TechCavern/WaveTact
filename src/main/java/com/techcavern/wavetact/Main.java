package com.techcavern.wavetact;

import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.LoadUtils;
import com.techcavern.wavetact.utils.objects.objectUtils.SimpleActionUtils;
import com.techcavern.wavetact.utils.objects.objectUtils.SimpleMessageUtils;
import org.slf4j.impl.SimpleLogger;

@SuppressWarnings("ConstantConditions")
public class Main {


    public static void main(String[] args) throws Exception {
        System.setProperty(SimpleLogger.SHOW_DATE_TIME_KEY, "true");
        System.setProperty(SimpleLogger.DATE_TIME_FORMAT_KEY, "[yyyy/MM/dd HH:mm:ss]");
        System.setProperty(SimpleLogger.LEVEL_IN_BRACKETS_KEY, "true");
        LoadUtils.initializeCommandlines();
        LoadUtils.parseCommandLineArguments(args);
        LoadUtils.registerCommands();
        SimpleActionUtils.loadSimpleActions();
        SimpleMessageUtils.loadSimpleMessages();
        LoadUtils.startThreads();
        GeneralRegistry.WaveTact.start();
    }
}
