/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.controller;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.LoadUtils;
import com.techcavern.wavetact.utils.objects.Command;
import com.techcavern.wavetact.utils.databaseUtils.SimpleActionUtils;
import com.techcavern.wavetact.utils.databaseUtils.SimpleMessageUtils;
import org.pircbotx.hooks.events.MessageEvent;


/**
 * @author jztech101
 */
public class DefCon extends Command {
    @CMD
    public DefCon() {
        super(GeneralUtils.toArray("defcon"), 9001, "defcon (-) locks down the bot to controller-only");
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args)
            throws Exception {

        if(args.length>=1 && args[0].equalsIgnoreCase("-")){
           GeneralRegistry.Commands.clear();
            GeneralRegistry.SimpleActions.clear();
            GeneralRegistry.SimpleMessages.clear();
            SimpleActionUtils.loadSimpleActions();
            SimpleMessageUtils.loadSimpleMessages();
            LoadUtils.registerCommands();
        }else{
            for(Command command:GeneralRegistry.Commands){
                command.setPermLevel(9001);
            }
        }
    }
}
