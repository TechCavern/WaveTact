package com.techcavern.wavetact.commands.utils;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.utils.objects.Command;
import com.techcavern.wavetact.utils.GeneralUtils;
import org.pircbotx.hooks.events.MessageEvent;

import java.net.InetAddress;
import java.net.Socket;

public class PingTime extends Command {
    @CMD
    public PingTime() {
        super(GeneralUtils.toArray("checkping cping cpi"), 0, "checkping [website] (port)");
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {
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
        event.getChannel().send().message("Ping Time: " + time + " milliseconds");

    }
}