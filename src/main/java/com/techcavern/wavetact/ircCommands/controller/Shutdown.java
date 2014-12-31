package com.techcavern.wavetact.ircCommands.controller;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.annot.ConCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.Registry;
import com.techcavern.wavetact.objects.IRCCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@IRCCMD
@ConCMD
public class Shutdown extends IRCCommand {

    public Shutdown() {
        super(GeneralUtils.toArray("shutdown stop"), 9001, null, "Shuts down the bot", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        //The consoleserver will initiate a shutdown for us.
        Registry.consoleServer.keepConsoleRunning = false;
    }
}

