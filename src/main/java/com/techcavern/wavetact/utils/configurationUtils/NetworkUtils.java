package com.techcavern.wavetact.utils.configurationUtils;

import com.techcavern.wavetact.utils.Registry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.eventListeners.*;
import com.techcavern.wavetact.utils.objects.NetProperty;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
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
                Registry.configs.put(name, config);
            }
        }
        PircBotX network;
        LinkedList<String> chans = new LinkedList<>();
        String nsPass;
        for (com.techcavern.wavetact.utils.fileUtils.Configuration c : Registry.configs.values()) {
            chans.clear();
            Collections.addAll(chans, c.getString("channels").split(", "));
            if (c.getString("nickserv").equalsIgnoreCase("False")) {
                nsPass = null;
            } else {
                nsPass = c.getString("nickserv");
            }
            String bindhost;
            if(c.getString("bindhost").equalsIgnoreCase("none")){
                bindhost = null;
            }else{
                bindhost = c.getString("bindhost");
            }
            network = createBot(nsPass, chans, c.getString("nick"), c.getString("server"), c.getInteger("port"), bindhost, c.getString("name"));
            Registry.WaveTact.addNetwork(network);
            Registry.CommandChars.add(new NetProperty(c.getString("prefix"), network));
            String authtype = c.getString("authtype").toLowerCase();
            if (authtype.startsWith("n")) {
                Registry.AuthType.add(new NetProperty("nickserv", network));
            } else if (authtype.startsWith("a")) {
                Registry.AuthType.add(new NetProperty("account", network));
            } else {
                Registry.AuthType.add(new NetProperty("nick", network));
            }
            Registry.NetworkName.add(new NetProperty(c.getString("name"), network));
            Registry.NetAdminAccess.add(new NetProperty(c.getString("netadminaccess"), network));
        }
    }

    public static void registerDevServer() {
        //      PircBotX Dev = createBot(null, Arrays.asList(GeneralUtils.toArray("#techcavern #testing")), "WaveTactDev", "irc.synirc.net");
        PircBotX Dev2 = createBot(null, Arrays.asList(GeneralUtils.toArray("")), "WaveTactDev", "irc.esper.net", 6667, null, "Esper");
        //      GeneralRegistry.WaveTact.addBot(Dev);
        Registry.WaveTact.addNetwork(Dev2);
        Registry.Controllers.add("JZTech101");
        //      GeneralRegistry.CommandChars.add(new NetProperty("@", Dev));
        Registry.CommandChars.add(new NetProperty("@", Dev2));
        Registry.AuthType.add(new NetProperty("nickserv", Dev2));
        Registry.NetworkName.add(new NetProperty("dev2", Dev2));
        Registry.NetAdminAccess.add(new NetProperty("True", Dev2));
        //     GeneralRegistry.NetworkName.add(new NetProperty("dev1", Dev));
    }

    public static PircBotX createBot(String nickservPassword, List<String> channels, String nick, String server, int port, String bindhost, String botName) {
        Configuration.Builder Net = new Configuration.Builder();
        Net.setName(nick);
        Net.setLogin("WaveTact");
        Net.setEncoding(Charset.isSupported("UTF-8") ? Charset.forName("UTF-8") : Charset.defaultCharset());
        if(bindhost != null){
            try {
                Net.setLocalAddress(InetAddress.getByName(bindhost));
            }catch(UnknownHostException e){
                System.out.println("Failed to resolve bindhost on " + botName);
                System.exit(0);
            }
        }
        Net.addServer(server, port);
        for (String channel : channels) {
            if (!channel.isEmpty())
                Net.addAutoJoinChannel(channel);
        }
        Net.setRealName(nick);
        Net.getListenerManager().addListener(new ChanMsgListener());
        Net.setAutoReconnect(true);
        Net.setAutoReconnectAttempts(5);
        Net.setAutoReconnectDelay(20000);
        Net.getListenerManager().addListener(new PartListener());
        Net.getListenerManager().addListener(new PrivMsgListener());
        Net.getListenerManager().addListener(new KickListener());
        Net.setChannelModeMessagePrefixes("+%@&~!");
        Net.setVersion("WaveTact 0.7.0");
        Net.setAutoReconnect(true);
        if (nickservPassword != null) {
            Net.setNickservPassword(nickservPassword);
            Net.setNickservDelayJoin(true);
        }
        return new PircBotX(Net.buildConfiguration());
    }
}
