package com.techcavern.wavetact.commands.dnsinfo;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.apache.http.conn.util.InetAddressUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@CMD
@GenCMD
public class Traceroute extends GenericCommand {

    public Traceroute() {
        super(GeneralUtils.toArray("traceroute trace"), 5, "traceroute [ip][domain]", "traces route to a server ", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        String IP = GeneralUtils.getIP(args[0], network);
        if(IP == null){
            IRCUtils.sendMessage(user, network, channel, "Host Unreachable", prefix);
        }else{
            String command = "";
            if(System.getProperty("os.name").startsWith("Windows") && InetAddressUtils.isIPv6Address(IP)) {
                command = "tracert6 " + IP;
            }else if(System.getProperty("os.name").startsWith("Windows") && InetAddressUtils.isIPv4Address(IP)){
                command = "tracert " + IP;
            }else if(InetAddressUtils.isIPv6Address(IP)){
                command = "traceroute6 " + IP;
            }else if(InetAddressUtils.isIPv4Address(IP)){
                command = "traceroute " + IP;
            }
            Process pinghost = Runtime.getRuntime().exec(command);
            BufferedReader buffereader = new BufferedReader(new InputStreamReader(pinghost.getInputStream()));
            String line = "";
            while ((line = buffereader.readLine()) != null) {
                if(!line.contains("* * *"))
                IRCUtils.sendMessage(user, network, channel, line, prefix);
            }
            buffereader.close();
        }

    }
}