package com.techcavern.wavetact.utils.configurationUtils;

import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.LoadUtils;
import com.techcavern.wavetact.utils.objects.NetProperty;
import org.pircbotx.PircBotX;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Created by jztech101 on 7/6/14.
 */
public class ConfigUtils {
    public static void registerConfigs() {
        File configfile = new File("config.properties");
        com.techcavern.wavetact.utils.fileUtils.Configuration config  = new com.techcavern.wavetact.utils.fileUtils.Configuration(configfile);
        GeneralRegistry.wolframalphaapikey = config.getString("wolframapi");
        GeneralRegistry.wundergroundapikey = config.getString("wundergroundapi");
        GeneralRegistry.dictionaryapikey = config.getString("dictionaryapi");
        GeneralRegistry.thesaurusapikey = config.getString("thesaurusapi");
    }
}
