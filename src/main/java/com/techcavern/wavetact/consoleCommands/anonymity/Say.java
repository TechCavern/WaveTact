/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.consoleCommands.anonymity;

import com.techcavern.wavetact.annot.ConCMD;
import com.techcavern.wavetact.objects.CommandIO;
import com.techcavern.wavetact.objects.ConsoleCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;

/**
 * @author jztech101
 */
@ConCMD
public class Say extends ConsoleCommand {

    public Say() {
        super(GeneralUtils.toArray("say"), "say [network] [channel] [something]", "Makes the bot say something");
    }

    @Override
    public void onCommand(String[] args, CommandIO commandIO) throws Exception {
        PircBotX network = IRCUtils.getBotByNetworkName(args[0]);
        String prefix = IRCUtils.getPrefix(network, args[1]);
        Channel chan;
        if (!prefix.isEmpty())
            chan = IRCUtils.getChannelbyName(network, args[1].replace(prefix, ""));
        else
            chan = IRCUtils.getChannelbyName(network, args[1]);
        if (chan != null)
            IRCUtils.sendMessage(network, chan, GeneralUtils.buildMessage(2, args.length, args).replace("\n", " "), prefix);
    }
}

