package com.techcavern.wavetact.commands.controller;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GeneralUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;


public class Shutdown extends GenericCommand {
    @CMD
    public Shutdown() {
        super(GeneralUtils.toArray("shutdown down"), 9001, "Shutdown (r)");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, String... args) throws Exception {
        if (args.length > 1 && args[0].equalsIgnoreCase("r")) {
            PircBotX botObject = Bot;
            IRCUtils.SendMessage(user, channel, "Restarting");
            botObject.stopBotReconnect();
            botObject.sendIRC().quitServer();
            Thread.sleep(20000);
            GeneralRegistry.WaveTact.addBot(botObject);
        } else {
            GeneralRegistry.WaveTact.stop();
            System.exit(0);

        }
    }
}
