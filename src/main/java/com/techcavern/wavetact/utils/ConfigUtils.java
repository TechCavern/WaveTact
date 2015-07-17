package com.techcavern.wavetact.utils;

import com.techcavern.wavetact.eventListeners.ConnectListener;
import org.jooq.Record;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

import static com.techcavern.wavetactdb.Tables.NETWORKS;


public class ConfigUtils {
    public static void registerNetworks() {
        PircBotX network;
        for (Record netRecord : DatabaseUtils.getNetworks()) {
            network = createNetwork(netRecord.getValue(NETWORKS.SERVERPASS), netRecord.getValue(NETWORKS.NICK), netRecord.getValue(NETWORKS.SERVER), netRecord.getValue(NETWORKS.PORT), netRecord.getValue(NETWORKS.BINDHOST), netRecord.getValue(NETWORKS.NAME));
            if (netRecord != null) {
                Registry.WaveTact.addNetwork(network);
                Registry.networkName.put(network, netRecord.getValue(NETWORKS.NAME));
                Registry.networkBot.put(netRecord.getValue(NETWORKS.NAME), network);
            }
        }
    }

    public static PircBotX createNetwork(String serverpass, String nick, String server, int port, String bindhost, String networkname) {
        if (nick.isEmpty() || server.isEmpty()) {
            DatabaseUtils.removeNetwork(networkname);
            System.out.println("Removing Server " + networkname);
            return null;
        } else if (IRCUtils.getNetworkByNetworkName(networkname) != null) {
            DatabaseUtils.removeNetwork(networkname);
            System.out.println("Removing Server " + networkname);
            return null;
        }

        Configuration.Builder Net = new Configuration.Builder();
        Net.setName(nick);
        Net.setLogin(nick);
        Net.setEncoding(Charset.isSupported("UTF-8") ? Charset.forName("UTF-8") : Charset.defaultCharset());
        if (bindhost != null) {
            try {
                Net.setLocalAddress(InetAddress.getByName(bindhost));
            } catch (UnknownHostException e) {
                System.out.println("Failed to resolve bindhost on " + networkname);
                System.exit(0);
            }
        }
        Net.addServer(server, port);
        Net.setRealName(nick);
        Net.getListenerManager().addListener(new ConnectListener());
        Net.setAutoReconnect(true);
        if (serverpass != null) {
            Net.setServerPassword(serverpass);
        }
        Net.setAutoReconnectAttempts(5);
        Net.setAutoReconnectDelay(20000);
        Net.setChannelPrefixes("#");
        Net.setUserLevelPrefixes("+%@&~!");
        Net.setVersion(Registry.VERSION);
        Net.setAutoReconnect(true);
        return new PircBotX(Net.buildConfiguration());
    }

}
