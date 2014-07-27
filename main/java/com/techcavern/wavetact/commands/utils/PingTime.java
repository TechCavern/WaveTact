package com.techcavern.wavetact.commands.utils;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.net.InetAddress;
import java.net.Socket;

public class PingTime extends GenericCommand {
    @CMD
    @GenCMD

    public PingTime() {
        super(GeneralUtils.toArray("checkping cping cpi"), 0, "checkping [website] (port)");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        int port;
        if (args.length < 2) {
            port = 80;
        } else {
            port = Integer.parseInt(args[1]);
        }
        Long time = System.currentTimeMillis();
        args[0] = args[0].replaceAll("http://|https://", "");
        Socket socket = new Socket(InetAddress.getByName(args[0]), port);
        socket.close();
        time = System.currentTimeMillis() - time;
        IRCUtils.SendMessage(user, channel, "Ping Time: " + time + " milliseconds", isPrivate);

    }
}