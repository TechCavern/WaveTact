/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.consoleCommands.utils;

import com.techcavern.wavetact.annot.ConCMD;
import com.techcavern.wavetact.objects.CommandIO;
import com.techcavern.wavetact.objects.ConsoleCommand;
import com.techcavern.wavetact.objects.NetProperty;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.Registry;
import org.pircbotx.PircBotX;

/**
 * @author jztech101
 */
@ConCMD
public class IRCRaw extends ConsoleCommand {

    public IRCRaw() {
        super(GeneralUtils.toArray("ircraw raw"), "ircraw [network] [raw message]", "Makes the bot send a raw message");
    }

    @Override
    public void onCommand(String command, String[] args, CommandIO commandIO) throws Exception {
        PircBotX network = IRCUtils.getBotByNetworkName(args[0]);
        Registry.MessageQueue.add(new NetProperty(GeneralUtils.buildMessage(1, args.length, args), network));
    }
}

