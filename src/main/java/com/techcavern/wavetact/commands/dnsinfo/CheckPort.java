package com.techcavern.wavetact.commands.dnsinfo;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

@CMD
@GenCMD
public class CheckPort extends GenericCommand {

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
        }catch(UnknownHostException e){
            //do nothing
        }
        if(IP == null){
            IRCUtils.sendMessage(user, network, channel, "Host Unreachable", prefix);
        }else {
            try {
                Socket socket = new Socket(IP, port);
                socket.close();
                IRCUtils.sendMessage(user, network, channel, port + " is open", prefix);
            } catch (ConnectException e) {
                if(e.getCause().getMessage().equals("java.net.ConnectException: Connection refused"))
                IRCUtils.sendMessage(user, network, channel, "Port " + port + " is closed on " + IP, prefix);
                else
                    IRCUtils.sendMessage(user, network, channel, "Host Unreachable", prefix);
            }
            ;
        }

    }
}