package com.techcavern.wavetact;

import org.pircbotx.Configuration;
import org.pircbotx.MultiBotManager;
import org.pircbotx.PircBotX;

import java.util.ArrayList;
import java.util.List;

public class WaveTact {
    public static MultiBotManager<PircBotX> botManager;

    public static void main(String[] args) {
        botManager = new MultiBotManager<>();
        for (Configuration<PircBotX> config : configurations()) {
            botManager.addBot(config);
        }
        botManager.start();
    }

    public static List<Configuration<PircBotX>> configurations() {
        @SuppressWarnings("UnnecessaryLocalVariable")
        List<Configuration<PircBotX>> configs = new ArrayList<>();
        /* TODO: Create Configurations */
        return configs;
    }
}
