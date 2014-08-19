package com.techcavern.wavetact.utils.configurationUtils;

import com.techcavern.wavetact.utils.GeneralRegistry;

import java.io.File;

/**
 * Created by jztech101 on 7/6/14.
 */
public class ConfigUtils {
    public static void registerConfigs() {
        File configfile = new File("config.properties");
        com.techcavern.wavetact.utils.fileUtils.Configuration config  = new com.techcavern.wavetact.utils.fileUtils.Configuration(configfile);
        GeneralRegistry.wolframalphaapikey = config.getString("wolframapi");
        GeneralRegistry.wundergroundapikey = config.getString("wundergroundapi");
        GeneralRegistry.wordnikapikey = config.getString("wordnikapi");
        GeneralRegistry.minecraftapikey = config.getString("minecraftapi");
    }
}
