package com.techcavern.wavetact.commands.dnsinfo;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.net.Socket;

@CMD
@GenCMD
public class PingTime extends GenericCommand {

    public PingTime() {
        super(GeneralUtils.toArray("pingtime checkping chping ptime"), 0, "pingtime [website] (port)", " Checks pingtime to a certain domain/address/ip/etc", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        int port;
        if (args.length < 2) {
            port = 80;
        } else {
            port = Integer.parseInt(args[1]);
        }
        Long time = System.currentTimeMillis();
        Socket socket = new Socket(GeneralUtils.getIP(args[0], network), port);
        socket.close();
        time = System.currentTimeMillis() - time;
        IRCUtils.sendMessage(user, network, channel, "Ping time: " + time + " milliseconds", prefix);

    }
}