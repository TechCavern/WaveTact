package com.techcavern.wavetact.commands.Utils;

import com.techcavern.wavetact.objects.Command;
import org.pircbotx.hooks.events.MessageEvent;

import java.net.InetAddress;
import java.net.Socket;

public class PingTime extends Command {
    public PingTime() {
        super("checkping", 0, "ping [website] (port)");
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
        Socket s = new Socket(InetAddress.getByName(args[0]), port);
        s.close();
        time = System.currentTimeMillis() - time;
        event.getChannel().send().message("Ping Time: " + time + " milliseconds");

    }
}