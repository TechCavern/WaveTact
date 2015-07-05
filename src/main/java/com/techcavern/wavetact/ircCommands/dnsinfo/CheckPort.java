package com.techcavern.wavetact.ircCommands.dnsinfo;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.net.*;

@IRCCMD
public class CheckPort extends IRCCommand {

    public CheckPort() {
        super(GeneralUtils.toArray("checkport cport"), 0, "checkport (+)[ip][domain] (port)", " Checks if port is open on a certain ip and port", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        boolean IPv6Priority = false;
        if(args[0].startsWith("+")){
            IPv6Priority = true;
            args[0] = args[0].replaceFirst("\\+","");
        }
        int port;
        if (args.length < 2) {
            port = 80;
        } else {
            port = Integer.parseInt(args[1]);
        }
        String IP = GeneralUtils.getIP(args[0], network, IPv6Priority);
        try {
            if (InetAddress.getByName(IP).isAnyLocalAddress()) {
                IRCUtils.sendMessage(user, network, channel, "I cannot find myself! :<", prefix);
                return;
            }
        } catch (UnknownHostException e) {
            //do nothing
        }
        if (IP == null) {
            ErrorUtils.sendError(user, "Host Unreachable");
        } else {
            try {
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(IP, port), 1000);
                socket.close();
                IRCUtils.sendMessage(user, network, channel, "Port " + port + " is open on " + IP, prefix);
            } catch (Exception e) {
                IRCUtils.sendMessage(user, network, channel, "Port " + port + " is closed on " + IP, prefix);
            }
        }

    }
}