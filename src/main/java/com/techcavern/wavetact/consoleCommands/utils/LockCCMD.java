/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.consoleCommands.utils;

import com.techcavern.wavetact.annot.ConCMD;
import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.CommandIO;
import com.techcavern.wavetact.objects.ConsoleCommand;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.DatabaseUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import static com.techcavern.wavetactdb.Tables.*;
import org.jooq.Record;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

/**
 * @author jztech101
 */
@ConCMD
public class LockCCMD extends ConsoleCommand {

    public LockCCMD() {
        super(GeneralUtils.toArray("lockmessage lcmsg lockmsg"), "lockmessage (-)[command]", "Locks a custom message");
    }

    @Override
    public void onCommand(String[] args, CommandIO commandIO) throws Exception {
        if (args[0].startsWith("-")) {
            Record command = DatabaseUtils.getCustomCommand(null, null, args[0].replaceFirst("-", ""));
            command.setValue(CUSTOMCOMMANDS.ISLOCKED, false);
            commandIO.getPrintStream().println("Custom command unlocked");

        } else {
            Record command = DatabaseUtils.getCustomCommand(null, null, args[0]);
            command.setValue(CUSTOMCOMMANDS.ISLOCKED, true);
            commandIO.getPrintStream().println("Custom command unlocked");
        }
    }
}
