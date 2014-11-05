package com.techcavern.wavetact.utils.configurationUtils;

import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.eventListeners.*;
import com.techcavern.wavetact.utils.objects.NetProperty;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


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

            bot = createBot(nsPass, chans, c.getString("nick"), c.getString("server"));
            GeneralRegistry.WaveTact.addBot(bot);
            GeneralRegistry.CommandChars.add(new NetProperty(c.getString("prefix"), bot));
            String authtype = c.getString("authtype").toLowerCase();
            if (authtype.startsWith("n")) {
                GeneralRegistry.AuthType.add(new NetProperty("nickserv", bot));
            } else if (authtype.startsWith("a")) {
                GeneralRegistry.AuthType.add(new NetProperty("account", bot));
            } else {
                GeneralRegistry.AuthType.add(new NetProperty("nick", bot));
            }
            GeneralRegistry.NetworkName.add(new NetProperty(c.getString("name"), bot));
        }
        GeneralRegistry.configs.clear();
    }

    public static void registerDevServer() {
        PircBotX Dev = createBot(null, Arrays.asList(GeneralUtils.toArray("#techcavern #testing")), "WaveTactDev", "irc.synirc.net");
        PircBotX Dev2 = createBot(null, Arrays.asList(GeneralUtils.toArray("#techcavern")), "WaveTactDev", "irc.esper.net");
        GeneralRegistry.WaveTact.addBot(Dev);
       GeneralRegistry.WaveTact.addBot(Dev2);
        GeneralRegistry.Controllers.add("JZTech101");
       GeneralRegistry.CommandChars.add(new NetProperty("@", Dev));
        GeneralRegistry.CommandChars.add(new NetProperty("@", Dev2));
        GeneralRegistry.AuthType.add(new NetProperty("nickserv", Dev2));
        GeneralRegistry.NetworkName.add(new NetProperty("dev2", Dev2));
        GeneralRegistry.NetworkName.add(new NetProperty("dev1", Dev));
    }

    public static PircBotX createBot(String nickservPassword, List<String> channels, String nick, String server) {
        Configuration.Builder<PircBotX> Net = new Configuration.Builder<>();
        Net.setName(nick);
        Net.setLogin("WaveTact");
        Net.setEncoding(Charset.isSupported("UTF-8") ? Charset.forName("UTF-8") : Charset.defaultCharset());
        Net.setServer(server, 6667);
        for (String channel : channels) {
            if (!channel.isEmpty())
                Net.addAutoJoinChannel(channel);
        }
        Net.setRealName(nick);
        Net.getListenerManager().addListener(new ChanMsgListener());
        Net.getListenerManager().addListener(new JoinListener());
        Net.getListenerManager().addListener(new PartListener());
        Net.getListenerManager().addListener(new PrivMsgListener());
        Net.getListenerManager().addListener(new KickListener());
        Net.setVersion("WaveTact 0.6.1");
        Net.setAutoReconnect(true);
        Net.setMaxLineLength(350);
        if (nickservPassword != null) {
            Net.setNickservPassword(nickservPassword);
        }
        return new PircBotX(Net.buildConfiguration());
    }
}
