/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.netadmin;

import com.google.common.collect.ImmutableSortedSet;
import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.NAdmCMD;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import jdk.nashorn.internal.ir.annotations.Immutable;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.List;


/**
 * @author jztech101
 */
@CMD
@NAdmCMD
public class Global extends GenericCommand {

    public Global() {
        super(GeneralUtils.toArray("global"), 20, "global [networkname/all]", "Sends a global to the network or to all networks");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        if(!args[0].equalsIgnoreCase("all")){
            PircBotX workingbot = GetUtils.getBotByNetworkName(args[0]);
            if(workingbot == null){
                IRCUtils.sendError(user, "Network does not exist");
                return;
            }
            if(workingbot == Bot){
               sendGlobal("[Global - " + user.getNick() +"] " + GeneralUtils.buildMessage(1, args.length, args), workingbot, user);
            }else{
                if(UserPermLevel >= 9001){
                    sendGlobal("[Global - " + user.getNick() +"] " + GeneralUtils.buildMessage(1, args.length, args), workingbot, user);
                }else{
                    IRCUtils.sendError(user, "Permission Denied");
                }
            }
        }else if(args[0].equalsIgnoreCase("all")&& UserPermLevel>=9001){
            for(PircBotX workingbot:GeneralRegistry.WaveTact.getBots()){
                sendGlobal("[Global - " + user.getNick() +"] " + GeneralUtils.buildMessage(1, args.length, args), workingbot, user);
            }

        }
    }
    public void sendGlobal(String message, PircBotX workingbot, User user){
        for(Channel workingchannel: workingbot.getUserBot().getChannels()){
            workingchannel.send().notice(message);
        }
    }
}
