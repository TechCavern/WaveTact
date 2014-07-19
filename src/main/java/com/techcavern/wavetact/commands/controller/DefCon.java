/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.controller;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.utils.*;
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
        super(GeneralUtils.toArray("defcon"), 9001, "defcon (-)(1)(2)(3)(4)(5) locks down the bot");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate,int UserPermLevel, String... args) throws Exception {


        if(args.length>=1 && args[0].equalsIgnoreCase("-")){
            initializeCommands();
            IRCUtils.SendMessage(user, channel, "DefCon OFF", isPrivate);
        }else{
            sPermLevel(9001);
            IRCUtils.SendMessage(user, channel, "DefCon ON", isPrivate);
        }
    }
    void initializeCommands(){
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
    }
    void sPermLevel(int PermLevel){
        for(String Com:GeneralRegistry.ControllerListCommands){
            GenericCommand Command = GetUtils.getCommand(Com);
            if(Command.getPermLevel() < PermLevel){
                Command.setPermLevel(PermLevel);
            }
        }
    }
}
