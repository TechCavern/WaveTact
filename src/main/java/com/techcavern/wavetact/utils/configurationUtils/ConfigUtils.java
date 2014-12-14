package com.techcavern.wavetact.utils.configurationUtils;

import com.techcavern.wavetact.utils.Constants;

import java.io.File;

public class ConfigUtils {
    public static void registerConfigs() {
        File configfile = new File("config.properties");
        com.techcavern.wavetact.utils.fileUtils.Configuration config = new com.techcavern.wavetact.utils.fileUtils.Configuration(configfile);
        Constants.wolframalphaapikey = config.getString("wolframapi");
        Constants.wundergroundapikey = config.getString("wundergroundapi");
        Constants.wordnikapikey = config.getString("wordnikapi");
        Constants.googleapikey = config.getString("googleapi");
    }
}
