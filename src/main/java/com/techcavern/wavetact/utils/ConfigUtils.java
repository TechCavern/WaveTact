package com.techcavern.wavetact.utils;

import com.techcavern.wavetact.eventListeners.ChanMsgListener;
import com.techcavern.wavetact.eventListeners.KickListener;
import com.techcavern.wavetact.eventListeners.PartListener;
import com.techcavern.wavetact.eventListeners.PrivMsgListener;
import com.techcavern.wavetact.objects.NetProperty;
import org.jooq.Record;
import org.jooq.tools.StringUtils;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import static com.techcavern.wavetactdb.Tables.*;


public class ConfigUtils {
    public static void registerNetworks() {
        PircBotX network;
        for(Record server: DatabaseUtils.getServers()) {
            network = createNetwork(server.getValue(SERVERS.NICKSERV), Arrays.asList(StringUtils.split(server.getValue(SERVERS.CHANNELS), ", ")), server.getValue(SERVERS.NICK), server.getValue(SERVERS.SERVER), server.getValue(SERVERS.PORT), server.getValue(SERVERS.BINDHOST), server.getValue(SERVERS.NAME));
            Registry.WaveTact.addNetwork(network);
            Registry.NetworkName.add(new NetProperty(server.getValue(SERVERS.NAME), network));
        }
    }
/** Will come back to at a future date
    public static void registerDevServer() {
        //      PircBotX Dev = createNetwork(null, Arrays.asList(GeneralUtils.toArray("#techcavern #testing")), "WaveTactDev", "irc.synirc.net");
        PircBotX Dev2 = createNetwork(null, Arrays.asList(GeneralUtils.toArray("")), "WaveTactDev", "irc.esper.net", 6667, null, "Esper");
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
**/
    public static PircBotX createNetwork(String nickservPassword, List<String> channels, String nick, String server, int port, String bindhost, String networkname) {
        Configuration.Builder Net = new Configuration.Builder();
        Net.setName(nick);
        Net.setLogin("WaveTact");
        Net.setEncoding(Charset.isSupported("UTF-8") ? Charset.forName("UTF-8") : Charset.defaultCharset());
        if(bindhost != null){
            try {
                Net.setLocalAddress(InetAddress.getByName(bindhost));
            }catch(UnknownHostException e){
                System.out.println("Failed to resolve bindhost on " + networkname);
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
