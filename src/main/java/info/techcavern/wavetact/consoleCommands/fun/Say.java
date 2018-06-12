/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.techcavern.wavetact.consoleCommands.fun;

import info.techcavern.wavetact.annot.ConCMD;
import info.techcavern.wavetact.objects.CommandIO;
import info.techcavern.wavetact.objects.ConsoleCommand;
import info.techcavern.wavetact.utils.GeneralUtils;
import info.techcavern.wavetact.utils.IRCUtils;
import info.techcavern.wavetact.annot.ConCMD;
import info.techcavern.wavetact.objects.CommandIO;
import info.techcavern.wavetact.objects.ConsoleCommand;
import info.techcavern.wavetact.utils.GeneralUtils;
import info.techcavern.wavetact.utils.IRCUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;

/**
 * @author jztech101
 */
@ConCMD
public class Say extends ConsoleCommand {

    public Say() {
        super(GeneralUtils.toArray("say act do msg"), "say [network] [channel] [something]", "Makes the bot say something");
    }

    @Override
    public void onCommand(String command, String[] args, CommandIO commandIO) throws Exception {
        PircBotX network = IRCUtils.getNetworkByNetworkName(args[0]);
        String prefix = IRCUtils.getPrefix(network, args[1]);
        Channel chan;
        if (!prefix.isEmpty())
            chan = IRCUtils.getChannelbyName(network, args[1].replace(prefix, ""));
        else
            chan = IRCUtils.getChannelbyName(network, args[1]);
        if (chan != null) {
            if (command.equalsIgnoreCase("act") || command.equalsIgnoreCase("do")) {
                IRCUtils.sendAction(network, chan,"[Console] " +  GeneralUtils.buildMessage(2, args.length, args).replace("\n", " "), prefix);
            } else {
                IRCUtils.sendMessage(network, chan, "[Console] " + GeneralUtils.buildMessage(2, args.length, args).replace("\n", " "), prefix);
            }
        }
    }
}

