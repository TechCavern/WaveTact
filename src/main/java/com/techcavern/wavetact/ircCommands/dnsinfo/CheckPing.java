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

@IRCCMD
public class CheckPing extends IRCCommand {

    public CheckPing() {
        super(GeneralUtils.toArray("checkping cping"), 0, "checkping (+)[ip][domain]", "Checks ping to a server", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        boolean IPv6Priority = false;
        if(args[0].startsWith("+")){
            IPv6Priority = true;
            args[0] = args[0].replaceFirst("\\+","");
        }
        String IP = GeneralUtils.getIP(args[0], network, IPv6Priority);
        if (IP == null) {
            ErrorUtils.sendError(user, "Host Unreachable");
        } else {
            String command = "";
            if (InetAddressUtils.isIPv6Address(IP)) {
                command = "ping6 -c 1 " + IP;
            } else if (InetAddressUtils.isIPv4Address(IP)) {
                command = "ping -c 1 " + IP;
            }
            long time = System.currentTimeMillis();
            Process pinghost = Runtime.getRuntime().exec(command);
            pinghost.waitFor();
            if (pinghost.exitValue() == 0) {
                IRCUtils.sendMessage(user, network, channel, IP + ": " + (System.currentTimeMillis() - time) + " milliseconds", prefix);
            } else {
                ErrorUtils.sendError(user, "Host Unreachable");
            }
        }

    }
}