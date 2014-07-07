package com.techcavern.wavetact.utils.configurationUtils;

import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.LoadUtils;
import com.techcavern.wavetact.utils.objects.CommandChar;
import org.pircbotx.PircBotX;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by jztech101 on 7/6/14.
 */
public class NetworkUtils {
    public static void registerNetworks() {
        File serversFolder = new File("servers/");
        serversFolder.mkdir();
        File[] files = serversFolder.listFiles();
        String name;
        com.techcavern.wavetact.utils.fileUtils.Configuration config;
        for (File f : files) {
            if (!f.isDirectory()) {
                name = f.getName();
                name = name.substring(0, f.getName().lastIndexOf('.'));
                config = new com.techcavern.wavetact.utils.fileUtils.Configuration(f);
                GeneralRegistry.configs.put(name, config);
            }
        }

        PircBotX bot;
        LinkedList<String> chans = new LinkedList<String>();
        String nsPass;
        for (com.techcavern.wavetact.utils.fileUtils.Configuration c : GeneralRegistry.configs.values()) {
            chans.clear();
            Collections.addAll(chans, c.getString("channels").split(", "));
            if (c.getString("nickserv").equalsIgnoreCase("False")) {
                nsPass = null;
            } else {
                nsPass = c.getString("nickserv");
            }

            bot = LoadUtils.createbot(nsPass, c.getString("name"), chans, c.getString("nick"), c.getString("server"));
            GeneralRegistry.WaveTact.addBot(bot);
            new CommandChar(c.getString("prefix"), bot);
        }
    }

    public static void registerDevServer() {
        List<String> Chans = Arrays.asList("#techcavern");
        PircBotX Dev = LoadUtils.createbot(null, "EsperNet", Chans, "WaveTactDev", "irc.electrocode.net");
        GeneralRegistry.WaveTact.addBot(Dev);
        GeneralRegistry.Controllers.add("JZTech101");
        GeneralRegistry.Controllers.add("deathcrazyuberlironman");

        new CommandChar("@", Dev);
    }
}
