/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.trusted;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.utils.objects.Command;
import com.techcavern.wavetact.utils.objects.CommandType;
import com.techcavern.wavetact.utils.objects.objectUtils.SimpleActionUtils;
import com.techcavern.wavetact.utils.objects.objectUtils.SimpleMessageUtils;
import com.techcavern.wavetact.utils.objects.SimpleAction;
import com.techcavern.wavetact.utils.objects.SimpleMessage;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.GetUtils;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * @author jztech101
 */
public class CustomCMD extends Command {

    @CMD
    public CustomCMD() {
        super(GeneralUtils.toArray("customcmd cmd custom cc"), 5, "customcmd [type(m/a)] (+/-)[Command] [permlevel] [Response]");
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {

        switch (args[0].toLowerCase()) {
            case "m": // message
                if (args[1].startsWith("-")) {
                    removeCommand(event, CommandType.MESSAGE, args[1].substring(1));
                } else if (args[1].startsWith("+")) {
                    modifyCommand(event, CommandType.MESSAGE, Integer.parseInt(args[2]), args[1].substring(1), GeneralUtils.buildMessage(3, args.length, args));
                } else {
                    addCommand(event, CommandType.MESSAGE, Integer.parseInt(args[2]), args[1], GeneralUtils.buildMessage(3, args.length, args));
                }
                break;
            case "a": // action
                if (args[1].startsWith("-")) {
                    removeCommand(event, CommandType.ACTION, args[1].substring(1));
                } else if (args[1].startsWith("+")) {
                    modifyCommand(event, CommandType.ACTION, Integer.parseInt(args[2]), args[1].substring(1), GeneralUtils.buildMessage(3, args.length, args));
                } else {
                    addCommand(event, CommandType.ACTION, Integer.parseInt(args[2]), args[1], GeneralUtils.buildMessage(3, args.length, args));
                }
                break;
        }
    }


    private void addCommand(MessageEvent<?> event, CommandType type, int accessLevel, String cmd, String msg) {
        if (GetUtils.getCommand(cmd) != null) {
            event.respond("Command already exists");
            return;
        }
        if (type == CommandType.ACTION) {
            GeneralRegistry.SimpleActions.add(new SimpleAction(cmd, accessLevel, msg, false));
            SimpleActionUtils.saveSimpleActions();
        } else if (type == CommandType.MESSAGE) {
            GeneralRegistry.SimpleMessages.add(new SimpleMessage(cmd, accessLevel, msg, false));
            SimpleMessageUtils.saveSimpleMessages();
        }
        event.respond("Command added");
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    private void modifyCommand(MessageEvent<?> event, CommandType type, int accessLevel, String command, String msg) {
        if (GetUtils.getCommand(command) != null) {
            Command cmd = GetUtils.getCommand(command);
            GeneralRegistry.Commands.remove(cmd);
            GeneralRegistry.SimpleActions.remove(cmd);
            GeneralRegistry.SimpleMessages.remove(cmd);
        }

        if (type == CommandType.ACTION) {
            GeneralRegistry.SimpleActions.add(new SimpleAction(command, accessLevel, msg, false));
            SimpleActionUtils.saveSimpleActions();
        } else if (type == CommandType.MESSAGE) {
            GeneralRegistry.SimpleMessages.add(new SimpleMessage(command, accessLevel, msg, false));
            SimpleMessageUtils.saveSimpleMessages();

        }
        event.respond("Command modified");
    }

    @SuppressWarnings({"SuspiciousMethodCalls", "ConstantConditions"})
    private void removeCommand(MessageEvent<?> event, CommandType type, String command) {
        Command cmd = null;
        if (type == CommandType.MESSAGE)
            cmd = GetUtils.getSimpleMessage(command);
        else if (type == CommandType.ACTION)
            cmd = GetUtils.getSimpleAction(command);

        if (cmd == null) {
            event.respond("Command does not exist");
        } else if (cmd.getLockedStatus()) {
            event.respond("Command is locked");
        } else {
            if (type == CommandType.MESSAGE) {
                GeneralRegistry.SimpleMessages.remove(cmd);
                SimpleMessageUtils.saveSimpleMessages();

            } else if (type == CommandType.ACTION) {
                GeneralRegistry.SimpleActions.remove(cmd);
                SimpleActionUtils.saveSimpleActions();
            }
            GeneralRegistry.Commands.remove(cmd);

            event.respond("Command removed");
        }
    }
}
