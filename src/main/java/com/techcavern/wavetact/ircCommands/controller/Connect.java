/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.ircCommands.controller;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.ConCMD;
import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.objects.IRCCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.concurrent.TimeUnit;

/**
 * @author jztech101
 */
@CMD
@ConCMD
public class Connect extends IRCCommand {

    public Connect() {
        super(GeneralUtils.toArray("connect"), 9001, "connect (+)(-)[networkname] (reason)", "Connects, reconnects or disconnects a network from a predefined network", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        boolean reconnect = false;
        boolean disconnect = false;
        if (args[0].startsWith("\\+")) {
            reconnect = true;
            args[0] = args[0].replaceFirst("\\+", "");
        } else if (args[0].startsWith("-")) {
            disconnect = true;
            args[0] = args[0].replaceFirst("-", "");
        }
        PircBotX workingnetwork = GetUtils.getBotByNetworkName(args[0]);
        if (workingnetwork == null) {
            ErrorUtils.sendError(user, "Network does not exist");
            return;
        }
        if (reconnect || disconnect) {
            if (workingnetwork.getState().equals(PircBotX.State.DISCONNECTED)) {
                ErrorUtils.sendError(user, "Bot currently disconnected");
                return;
            } else {
                IRCUtils.sendMessage(user, network, channel, "Disconnecting...", prefix);
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
            ErrorUtils.sendError(user, "Bot currently connected");
        } else {
            workingnetwork.startBot();
        }
        IRCUtils.sendMessage(user, network, channel, "Reconnecting...", prefix);

    }
}
