/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.controller;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.ConCMD;
import com.techcavern.wavetact.annot.NAdmCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.concurrent.TimeUnit;

/**
 * @author jztech101
 */
@CMD
@ConCMD
public class Connect extends GenericCommand {

    public Connect() {
        super(GeneralUtils.toArray("connect"), 9001, "connect (%)(-)[networkname] (Reason)", "connects, reconnects or disconnects a bot from a predefined network");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        boolean reconnect = false;
        boolean disconnect = false;
        if(args[0].startsWith("%")){
            reconnect=true;
            args[0] = args[0].replaceFirst("\\%", "");
        }else if(args[0].startsWith("-")){
            disconnect=true;
            args[0] = args[0].replaceFirst("\\-", "");
        }
        PircBotX workingbot = GetUtils.getBotByNetworkName(args[0]);
        if(workingbot == null){
            IRCUtils.sendError(user, "Network does not exist");
            return;
        }
        if(reconnect){
           workingbot.sendIRC().quitServer(GeneralUtils.buildMessage(1, args.length, args));
           do{
                TimeUnit.SECONDS.sleep(5);
            }while(workingbot.getState().equals(PircBotX.State.CONNECTED));
        }else if(disconnect){
            workingbot.sendIRC().quitServer(GeneralUtils.buildMessage(1, args.length, args));
            return;
        }
            if(workingbot.getState().equals(PircBotX.State.CONNECTED)){
                IRCUtils.sendError(user, "Bot Currently Connected");
            }else{
                workingbot.startBot();
            }

    }
}
