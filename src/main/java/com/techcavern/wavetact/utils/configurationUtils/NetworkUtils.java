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
        LinkedList<String> chans = new LinkedList<>();
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
 //       PircBotX Dev = LoadUtils.createbot(null, "SynIRC", Arrays.asList("##powder-bots"), "WaveTactDev", "irc.freenode.net");
        PircBotX Dev2 = LoadUtils.createbot(null, "EsperNet", Arrays.asList("#batbot"), "WaveTactDev", "irc.esper.net");
   //     GeneralRegistry.WaveTact.addBot(Dev);
        GeneralRegistry.WaveTact.addBot(Dev2);
        GeneralRegistry.Controllers.add("JZTech101");
     //   new CommandChar("@", Dev);
        new CommandChar("@", Dev2);
    }
}
