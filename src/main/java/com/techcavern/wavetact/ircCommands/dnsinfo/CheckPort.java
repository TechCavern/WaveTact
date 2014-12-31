package com.techcavern.wavetact.ircCommands.dnsinfo;

import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

@IRCCMD
@GenCMD
public class CheckPort extends IRCCommand {

    public CheckPort() {
        super(GeneralUtils.toArray("checkport cport"), 0, "checkport [ip][domain] (port)", " Checks if port is open on a certain ip and port", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        int port;
        if (args.length < 2) {
            port = 80;
        } else {
            port = Integer.parseInt(args[1]);
        }
        String IP = GeneralUtils.getIP(args[0], network);
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
                Socket socket = new Socket(IP, port);
                socket.close();
                IRCUtils.sendMessage(user, network, channel, "Port " + port + " is open on " + IP, prefix);
            } catch (ConnectException e) {
                if (e.getMessage().equals("Connection refused"))
                    IRCUtils.sendMessage(user, network, channel, "Port " + port + " is closed on " + IP, prefix);
                else
                    ErrorUtils.sendError(user, "Host Unreachable");
            }
            ;
        }

    }
}