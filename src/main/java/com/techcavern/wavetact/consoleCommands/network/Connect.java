package com.techcavern.wavetact.consoleCommands.network;

import com.techcavern.wavetact.annot.ConCMD;
import com.techcavern.wavetact.objects.CommandIO;
import com.techcavern.wavetact.objects.ConsoleCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.pircbotx.PircBotX;

import java.util.concurrent.TimeUnit;

@ConCMD
public class Connect extends ConsoleCommand {

    public Connect() {
        super(GeneralUtils.toArray("connect reconnect disconnect"), "connect [networkname] (reason)", "Connects, reconnects or disconnects a network from a predefined network");
    }

    @Override
    public void onCommand(String command, String[] args, CommandIO commandIO) throws Exception {
        boolean reconnect = false;
        boolean disconnect = false;
        if (command.equalsIgnoreCase("reconnect")) {
            reconnect = true;
        } else if (command.equalsIgnoreCase("disconnect")) {
            disconnect = true;
        }
        PircBotX workingnetwork = IRCUtils.getBotByNetworkName(args[0]);
        if (workingnetwork == null) {
            commandIO.getPrintStream().println("Network does not exist");
            return;
        }
        if (reconnect || disconnect) {
            if (workingnetwork.getState().equals(PircBotX.State.DISCONNECTED)) {
                commandIO.getPrintStream().println("Bot currently disconnected");
                return;
            } else {
                commandIO.getPrintStream().println("Disconnecting...");
                workingnetwork.sendIRC().quitServer(GeneralUtils.buildMessage(1, args.length, args));
                workingnetwork.stopBotReconnect();
            }
        }
        if (disconnect) {
            return;
        } else if (reconnect) {
            do {
                TimeUnit.SECONDS.sleep(5);
            } while (workingnetwork.getState().equals(PircBotX.State.CONNECTED));
        }
        if (workingnetwork.getState().equals(PircBotX.State.CONNECTED)) {
            commandIO.getPrintStream().println("Bot currently connected");
        } else {
            workingnetwork.startBot();
        }
        commandIO.getPrintStream().println("Reconnecting...");
    }
}
