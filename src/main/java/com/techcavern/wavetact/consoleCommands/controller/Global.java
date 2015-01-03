package com.techcavern.wavetact.consoleCommands.controller;

import com.techcavern.wavetact.annot.ConCMD;
import com.techcavern.wavetact.objects.CommandIO;
import com.techcavern.wavetact.objects.ConsoleCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.pircbotx.PircBotX;

import java.util.concurrent.TimeUnit;

@ConCMD
public class Global extends ConsoleCommand {

    public Global() {
        super(GeneralUtils.toArray("global"), "global [message]", "Sends a global to all channels on all networks");
    }
    @Override
    public void onCommand(String[] args, CommandIO commandIO) throws Exception {
        IRCUtils.sendGlobal(GeneralUtils.buildMessage(0, args.length, args), null);
    }
}
