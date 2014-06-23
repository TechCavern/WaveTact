/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.trusted;

import com.techcavern.wavetact.objects.Command;
import com.techcavern.wavetact.objects.SimpleAction;
import com.techcavern.wavetact.objects.SimpleMessage;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.IRCUtils;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * @author jztech101
 */
public class CustomCMD extends Command {

    public CustomCMD() {
        super("customcmd", 5, "customcmd [type(m/a)] (+/-)[Command] [permlevel] [Response]");
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {

        switch (args[0].toLowerCase()) {
            case "m": // message
                if (args[1].startsWith("-")) {
                    removeCommand(event, Type.MESSAGE, args[1].substring(1));
                } else if (args[1].startsWith("+")) {
                    modifyCommand(event, Type.MESSAGE, Integer.parseInt(args[2]), args[1].substring(1), buildMessage(args));
                } else {
                    addCommand(event, Type.MESSAGE, Integer.parseInt(args[2]), args[1], buildMessage(args));
                }
                break;
            case "a": // action
                if (args[1].startsWith("-")) {
                    removeCommand(event, Type.ACTION, args[1].substring(1));
                } else if (args[1].startsWith("+")) {
                    modifyCommand(event, Type.ACTION, Integer.parseInt(args[2]), args[1].substring(1), buildMessage(args));
                } else {
                    addCommand(event, Type.ACTION, Integer.parseInt(args[2]), args[1], buildMessage(args));
                }
                break;
        }
    }

    private String buildMessage(String[] args) {
        StringBuilder builder = new StringBuilder();
        for (int i = 3; i < args.length; i++) {
            builder.append(args[i]);
            builder.append(' ');
        }
        return builder.toString().trim();
    }

    private void addCommand(MessageEvent<?> event, Type type, int accessLevel, String cmd, String msg) {
        if (IRCUtils.getCommand(cmd) != null) {
            event.respond("Command already exists");
            return;
        }
        if (type == Type.ACTION) {
            GeneralRegistry.SimpleActions.add(new SimpleAction(cmd, accessLevel, msg, false));
            IRCUtils.saveSimpleActions();
        }
        else if (type == Type.MESSAGE) {
            GeneralRegistry.SimpleMessages.add(new SimpleMessage(cmd, accessLevel, msg, false));
            IRCUtils.saveSimpleMessages();
        }
        event.respond("Command added");
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    private void modifyCommand(MessageEvent<?> event, Type type, int accessLevel, String command, String msg) {
        if (IRCUtils.getCommand(command) != null) {
            Command cmd = IRCUtils.getCommand(command);
            GeneralRegistry.Commands.remove(cmd);
            GeneralRegistry.SimpleActions.remove(cmd);
            GeneralRegistry.SimpleMessages.remove(cmd);
        }

        if (type == Type.ACTION) {
            GeneralRegistry.SimpleActions.add(new SimpleAction(command, accessLevel, msg, false));
            IRCUtils.saveSimpleActions();
        }
        else if (type == Type.MESSAGE) {
            GeneralRegistry.SimpleMessages.add(new SimpleMessage(command, accessLevel, msg, false));
            IRCUtils.saveSimpleMessages();

        }
        event.respond("Command modified");
    }

    @SuppressWarnings({"SuspiciousMethodCalls", "ConstantConditions"})
    private void removeCommand(MessageEvent<?> event, Type type, String command) {
        Command cmd = null;
        if (type == Type.MESSAGE)
            cmd = IRCUtils.getSimpleMessage(command);
        else if (type == Type.ACTION)
            cmd = IRCUtils.getSimpleAction(command);

        if (cmd == null) {
            event.respond("Command does not exist");
        } else if (cmd.getLockedStatus()) {
            event.respond("Command is locked");
        } else {
            if (type == Type.MESSAGE) {
                GeneralRegistry.SimpleMessages.remove(cmd);
                IRCUtils.saveSimpleMessages();

            }
            else if (type == Type.ACTION) {
                GeneralRegistry.SimpleActions.remove(cmd);
                IRCUtils.saveSimpleActions();
            }
            GeneralRegistry.Commands.remove(cmd);

            event.respond("Command removed");
        }
    }

    private enum Type {
        ACTION,
        MESSAGE
    }
}
