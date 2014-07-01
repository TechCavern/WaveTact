package com.techcavern.wavetact.commands.controller;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.objects.Command;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GeneralUtils;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;


public class Shutdown extends Command {
    @CMD
    public Shutdown() {
        super(GeneralUtils.toArray("Shutdown down"), 9001, "Shutdown (r)");
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
