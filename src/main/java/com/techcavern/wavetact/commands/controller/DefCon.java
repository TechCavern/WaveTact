/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.controller;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.ConCMD;
import com.techcavern.wavetact.utils.Registry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.LoadUtils;
import com.techcavern.wavetact.utils.databaseUtils.SimpleActionUtils;
import com.techcavern.wavetact.utils.databaseUtils.SimpleMessageUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;


/**
 * @author jztech101
 */
@CMD
@ConCMD
public class DefCon extends GenericCommand {

    public DefCon() {
        super(GeneralUtils.toArray("defcon"), 9001, "defcon (-)(1)(2)(3)(4)(5)", "Locks down the network", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        if (args.length >= 1) {
            if (args[0].startsWith("-")) {
                initializeCommands();
                IRCUtils.sendGlobal("Defcon OFF", user);
            }
            switch (Integer.parseInt(args[0])) {
                case 1:
                    sPermLevel(9001);
                    break;
                case 2:
                    sPermLevel(20);
                    break;
                case 3:
                    sPermLevel(18);
                    break;
                case 4:
                    sPermLevel(10);
                    break;
                case 5:
                    sPermLevel(5);
                    break;
            }
            IRCUtils.sendGlobal("Defcon " + args[0], user);
        } else {
            sPermLevel(9001);
            IRCUtils.sendGlobal("Defcon 1", user);
        }
    }

    void initializeCommands() {
        Registry.AllCommands.clear();
        Registry.GenericCommands.clear();
        Registry.SimpleActions.clear();
        Registry.SimpleMessages.clear();
        Registry.TrustedCommands.clear();
        Registry.ChanHalfOpCommands.clear();
        Registry.ControllerCommands.clear();
        Registry.ChanOwnOpCommands.clear();
        Registry.NetAdminCommands.clear();
        Registry.ChanOpCommands.clear();
        Registry.ChanAdminCommands.clear();
        SimpleActionUtils.loadSimpleActions();
        SimpleMessageUtils.loadSimpleMessages();
        LoadUtils.registerCommands();
    }

    void sPermLevel(int PermLevel) {
        for (GenericCommand Command : Registry.AllCommands) {
            if (Command.getPermLevel() < PermLevel) {
                Command.setPermLevel(PermLevel);
            }
        }
    }
}
