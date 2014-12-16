package com.techcavern.wavetact.utils.configurationUtils;

import com.techcavern.wavetact.utils.Registry;

import java.io.File;

public class ConfigUtils {
    public static void registerConfigs() {
        File configfile = new File("config.properties");
        com.techcavern.wavetact.utils.fileUtils.Configuration config = new com.techcavern.wavetact.utils.fileUtils.Configuration(configfile);
        Registry.wolframalphaapikey = config.getString("wolframapi");
        Registry.wundergroundapikey = config.getString("wundergroundapi");
        Registry.wordnikapikey = config.getString("wordnikapi");
        Registry.googleapikey = config.getString("googleapi");
    }
}
