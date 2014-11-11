package com.techcavern.wavetact.utils.configuration;

import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.file.Configuration;

import java.io.File;

public class ConfigUtils {
    public static void registerConfigs() {
        try {
            Configuration config = new Configuration(new File("config.properties"));
            GeneralRegistry.wolframalphaapikey = config.getString("wolframapi");
            GeneralRegistry.wundergroundapikey = config.getString("wundergroundapi");
            GeneralRegistry.wordnikapikey = config.getString("wordnikapi");
            GeneralRegistry.googleapikey = config.getString("googleapi");
        } catch (Configuration.ConfigurationException e) {
            e.printStackTrace();
        }
    }
}
