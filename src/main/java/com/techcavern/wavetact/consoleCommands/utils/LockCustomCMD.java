/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.consoleCommands.utils;

import com.techcavern.wavetact.annot.ConCMD;
import com.techcavern.wavetact.objects.CommandIO;
import com.techcavern.wavetact.objects.ConsoleCommand;
import com.techcavern.wavetact.utils.DatabaseUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import org.jooq.Record;

import static com.techcavern.wavetactdb.Tables.CUSTOMCOMMANDS;

/**
 * @author jztech101
 */
@ConCMD
public class LockCustomCMD extends ConsoleCommand {

    public LockCustomCMD() {
        super(GeneralUtils.toArray("lockcustomcmd lockccmd lccmd"), "lockcustomcmd (-)[command]", "Locks/Unlocks a custom command");
    }

    @Override
    public void onCommand(String command, String[] args, CommandIO commandIO) throws Exception {
        Record customCommand = DatabaseUtils.getChannelCustomCommand(null, null, args[0].replaceFirst("-", ""));
        if (customCommand != null) {
            if (args[0].startsWith("-")) {
                customCommand.setValue(CUSTOMCOMMANDS.ISLOCKED, false);
                commandIO.getPrintStream().println("Custom command unlocked");
            } else {
                customCommand.setValue(CUSTOMCOMMANDS.ISLOCKED, true);
                commandIO.getPrintStream().println("Custom command locked");
            }
            DatabaseUtils.updateCustomCommand(customCommand);
        }
    }
}
