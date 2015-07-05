package com.techcavern.wavetact.ircCommands.dnsinfo;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.apache.http.conn.util.InetAddressUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@IRCCMD
public class Traceroute extends IRCCommand {

    public Traceroute() {
        super(GeneralUtils.toArray("traceroute trace"), 5, "traceroute (+)[ip][domain]", "traces route to a server ", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        boolean IPv6Priority = false;
        if(args[0].startsWith("+")){
            IPv6Priority = true;
            args[0] = args[0].replaceFirst("\\+","");
        }
        String IP = GeneralUtils.getIP(args[0], network, IPv6Priority);
        if (IP == null) {
            ErrorUtils.sendError(user, "Host Unreachable");
        } else {
            String traceCommand = "";
            if (InetAddressUtils.isIPv6Address(IP)) {
                traceCommand = "traceroute6 " + IP;
            } else if (InetAddressUtils.isIPv4Address(IP)) {
                traceCommand = "traceroute " + IP;
            }
            Process pinghost = Runtime.getRuntime().exec(traceCommand);
            BufferedReader buffereader = new BufferedReader(new InputStreamReader(pinghost.getInputStream()));
            String line = "";
            while ((line = buffereader.readLine()) != null) {
                if (!line.contains("* * *"))
                    IRCUtils.sendMessage(user, network, channel, line, prefix);
            }
            buffereader.close();
        }

    }
}