/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.controller;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.ConCMD;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.LoadUtils;
import com.techcavern.wavetact.utils.databaseUtils.SimpleActionUtils;
import com.techcavern.wavetact.utils.databaseUtils.SimpleMessageUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.pircbotz.Channel;
import org.pircbotz.PircBotZ;
import org.pircbotz.User;


/**
 * @author jztech101
 */
public class DefCon extends GenericCommand {
    @CMD
    @ConCMD
    public DefCon() {
        super(GeneralUtils.toArray("defcon"), 9001, "defcon (-)(1)(2)(3)(4)(5) locks down the bot");
    }

    @Override
    public void onCommand(User user, PircBotZ Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        if (args.length >= 1) {
            if (args[0].startsWith("-")) {
                initializeCommands();
                IRCUtils.SendMessage(user, channel, "DefCon OFF", isPrivate);
            }
            switch (Integer.parseInt(args[0])) {
                case 1:
                    sPermLevel(5);
                    break;
                case 2:
                    sPermLevel(10);
                    break;
                case 3:
                    sPermLevel(18);
                    break;
                case 4:
                    sPermLevel(20);
                    break;
                case 5:
                    sPermLevel(9001);
                    break;
            }
            IRCUtils.SendMessage(user, channel, "DefCon ON", isPrivate);
        } else {
            sPermLevel(9001);
            IRCUtils.SendMessage(user, channel, "DefCon ON", isPrivate);
        }
    }

    void initializeCommands() {
        GeneralRegistry.AllCommands.clear();
        GeneralRegistry.GenericCommands.clear();
        GeneralRegistry.FunCommands.clear();
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

    void sPermLevel(int PermLevel) {
        for (GenericCommand Command : GeneralRegistry.AllCommands) {
            if (Command.getPermLevel() < PermLevel) {
                Command.setPermLevel(PermLevel);
            }
        }
    }
}
