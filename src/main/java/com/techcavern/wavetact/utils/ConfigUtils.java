package com.techcavern.wavetact.utils;

import com.techcavern.wavetact.eventListeners.ConnectListener;
import com.techcavern.wavetact.objects.NetRecord;
import org.jooq.Record;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

import static com.techcavern.wavetactdb.Tables.SERVERS;


public class ConfigUtils {
    public static void registerNetworks() {
        PircBotX network;
        for (Record server : DatabaseUtils.getServers()) {
            network = createNetwork(server.getValue(SERVERS.SERVERPASS), server.getValue(SERVERS.NICK), server.getValue(SERVERS.SERVER), server.getValue(SERVERS.PORT), server.getValue(SERVERS.BINDHOST), server.getValue(SERVERS.NAME));
            if (network != null) {
                Registry.WaveTact.addNetwork(network);
                Registry.NetworkName.add(new NetRecord(server.getValue(SERVERS.NAME), network));
            }
        }
    }

    public static PircBotX createNetwork(String serverpass, String nick, String server, int port, String bindhost, String networkname) {
        if (nick.isEmpty() || server.isEmpty()) {
            DatabaseUtils.removeServer(networkname);
            System.out.println("Removing Server " + networkname);
            return null;
        } else if (IRCUtils.getBotByNetworkName(networkname) != null) {
            DatabaseUtils.removeServer(networkname);
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
        Net.setVersion(Registry.Version);
        Net.setAutoReconnect(true);
        return new PircBotX(Net.buildConfiguration());
    }

}
