package com.techcavern.wavetact.commands.controller;

import com.techcavern.wavetact.objects.Command;
import com.techcavern.wavetact.utils.GeneralRegistry;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;


/**
 * Created by jztech101 on 6/23/14.
 */
public class Shutdown extends Command {
    public Shutdown() {
        super("Shutdown", 9001);
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {
        if (args[0].equalsIgnoreCase("r")) {
            PircBotX c = event.getBot();
            event.getChannel().send().message("Restarting Bot");
            c.stopBotReconnect();
            c.sendIRC().quitServer();
            Thread.sleep(20000);
            GeneralRegistry.WaveTact.addBot(c);
        } else {
            GeneralRegistry.WaveTact.stop();
            System.exit(0);
            event.getChannel().send().message("Stopping Bot");

        }
    }
}
