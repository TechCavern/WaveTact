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
import com.techcavern.wavetact.utils.objects.GenericCommand;
import jdk.nashorn.internal.ir.annotations.Immutable;
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
        super(GeneralUtils.toArray("global"), 20, "global [net/network]", "Sends a global to the network or to all networks");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        if(args[0].equalsIgnoreCase("net") || args[0].equalsIgnoreCase("network")){
            for(Channel chan : Bot.getUserBot().getChannels()){
                chan.send().notice("Global [" + user.getNick() + "]:" +  GeneralUtils.buildMessage(1, args.length, args) );
            }
        }else{
            if(UserPermLevel == 9001){
                for(PircBotX botx : GeneralRegistry.WaveTact.getBots()){
                    for(Channel chan : botx.getUserBot().getChannels()){
                        chan.send().notice("Global [" + user.getNick() + "]:" +  GeneralUtils.buildMessage(0, args.length, args) );
                    }
                }
            }
        }
    }
}
