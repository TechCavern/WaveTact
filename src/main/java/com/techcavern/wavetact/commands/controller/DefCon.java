/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.controller;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.LoadUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import com.techcavern.wavetact.utils.databaseUtils.SimpleActionUtils;
import com.techcavern.wavetact.utils.databaseUtils.SimpleMessageUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;


/**
 * @author jztech101
 */
public class DefCon extends GenericCommand {
    @CMD
    public DefCon() {
        super(GeneralUtils.toArray("defcon"), 9001, "defcon (-) locks down the bot to controller-only");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate,int UserPermLevel, String... args) throws Exception {


        if(args.length>=1 && args[0].equalsIgnoreCase("-")){
           GeneralRegistry.GenericCommands.clear();
            GeneralRegistry.SimpleActions.clear();
            GeneralRegistry.SimpleMessages.clear();
            GeneralRegistry.TrustedCommands.clear();
            GeneralRegistry.ChanHalfOpCommands.clear();
            GeneralRegistry.ControllerCommands.clear();
            GeneralRegistry.ChanOwnerCommands.clear();
            GeneralRegistry.GlobalCommands.clear();
            GeneralRegistry.ChanOpCommands.clear();
            GeneralRegistry.ChanFounderCommands.clear();
            GeneralRegistry.AnonymonityCommands.clear();
            SimpleActionUtils.loadSimpleActions();
            SimpleMessageUtils.loadSimpleMessages();
            LoadUtils.registerCommands();
            IRCUtils.SendMessage(user, channel, "DefCon OFF", isPrivate);
        }else{
            for(GenericCommand command:GeneralRegistry.GenericCommands){
                command.setPermLevel(9001);
            }
            for(GenericCommand command:GeneralRegistry.TrustedCommands){
                command.setPermLevel(9001);
            }
            for(GenericCommand command:GeneralRegistry.ControllerCommands){
                command.setPermLevel(9001);
            }
            for(GenericCommand command:GeneralRegistry.ChanOpCommands){
                command.setPermLevel(9001);
            }
            for(GenericCommand command:GeneralRegistry.ChanOwnerCommands){
                command.setPermLevel(9001);
            }
            for(GenericCommand command:GeneralRegistry.ChanFounderCommands){
                command.setPermLevel(9001);
            }
            for(GenericCommand command:GeneralRegistry.ChanHalfOpCommands){
                command.setPermLevel(9001);
            }
            for(GenericCommand command:GeneralRegistry.AnonymonityCommands){
                command.setPermLevel(9001);
            }
            for(GenericCommand command:GeneralRegistry.GlobalCommands){
                command.setPermLevel(9001);
            }
            IRCUtils.SendMessage(user, channel, "DefCon ON", isPrivate);
        }
    }
}
