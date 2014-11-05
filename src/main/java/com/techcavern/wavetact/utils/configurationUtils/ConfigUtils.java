package com.techcavern.wavetact.utils.configurationUtils;

import com.techcavern.wavetact.utils.GeneralRegistry;

import java.io.File;

public class ConfigUtils {
    public static void registerConfigs() {
        File configfile = new File("config.properties");
        com.techcavern.wavetact.utils.fileUtils.Configuration config = new com.techcavern.wavetact.utils.fileUtils.Configuration(configfile);
        GeneralRegistry.wolframalphaapikey = config.getString("wolframapi");
        GeneralRegistry.wundergroundapikey = config.getString("wundergroundapi");
        GeneralRegistry.wordnikapikey = config.getString("wordnikapi");
        if(config.getString("netadminaccess").equalsIgnoreCase("true")){
            GeneralRegistry.allownetadminaccess = true;
        }
    }
}
