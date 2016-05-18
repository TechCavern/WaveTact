package com.techcavern.wavetact.ircCommands.dnsinfo;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.apache.http.conn.util.InetAddressUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@IRCCMD
public class CheckPing extends IRCCommand {

    public CheckPing() {
        super(GeneralUtils.toArray("checkping cping cpi checkping6 cping6 cpi6"), 1, "checkping (+)[ip][domain]", "Checks ping to a server", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        boolean IPv6Priority = false;
        if (command.endsWith("6")) {
            IPv6Priority = true;
        }
        String IP = GeneralUtils.getIP(args[0], network, IPv6Priority);
        if (IP == null) {
            IRCUtils.sendError(user, network, channel, "Host Unreachable", prefix);
        } else {
            String pingCommand = "";
            if (InetAddressUtils.isIPv6Address(IP)) {
                pingCommand = "ping6 -c 1 " + IP;
            } else if (InetAddressUtils.isIPv4Address(IP)) {
                pingCommand = "ping -c 1 " + IP;
            }
            Process pinghost = Runtime.getRuntime().exec(pingCommand);
            BufferedReader buffereader = new BufferedReader(new InputStreamReader(pinghost.getInputStream()));
            boolean haslines = false;
            String line;
            while ((line = buffereader.readLine()) != null) {
                if (line.contains("time=")) {
                    haslines = true;
                    String[] ips = line.split(" ");
                    IRCUtils.sendMessage(user, network, channel, "[" + IP + "] " + ips[ips.length - 2].replace("time=", "") + " milliseconds", prefix);
                }
            }
            buffereader.close();
            if (!haslines) {
                IRCUtils.sendError(user, network, channel, "Host Unreachable", prefix);
            }
        }

    }
}