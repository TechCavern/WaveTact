/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands;

import com.techcavern.wavetact.utils.*;
import com.techcavern.wavetact.utils.AbstractCommand;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * @author jztech101
 */
public class CustomCMD extends AbstractCommand {

    public CustomCMD() {
        super("customcmd", 0);
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {
        if (args[0].equalsIgnoreCase("m")) {
            if (args[1].startsWith("-")) {
                if (IRCUtils.getCommand(args[1].replaceFirst("-", "")).getPermLevel() <= PermUtils.getPermLevel(event.getBot(), event.getUser(), event.getChannel())) {
                    GeneralRegistry.Commands.remove(IRCUtils.getCommand(args[1].replaceFirst("-", "")));
                    //       GeneralRegistry.SimpleMessage.remove(IRCUtils.getCommand(args[1].replaceFirst("-", "")));
                    event.getChannel().send().message("Command Removed");
                } else {
                    event.getChannel().send().message("Permission Denied");
                }
            } else if (args[1].startsWith("+")) {
                if (IRCUtils.getCommand(args[1].replaceFirst("-", "")).getPermLevel() <= PermUtils.getPermLevel(event.getBot(), event.getUser(), event.getChannel())) {

                    GeneralRegistry.Commands.remove(IRCUtils.getCommand(args[1].replaceFirst("\\+", "")));
                    String[] s = ArrayUtils.remove(args, 0);
                    s = ArrayUtils.remove(s, 0);
                    s = ArrayUtils.remove(s, 0);
                    String sj = StringUtils.join(s, ' ');
                    SimpleMessage c = new SimpleMessage(args[1].replaceFirst("\\+", ""), Integer.parseInt(args[2]), sj, false);
                    GeneralRegistry.SimpleMessage.add(c);
                    //        GeneralRegistry.Commands.add(c);
                    IRCUtils.RegisterExistingSimpleMessages();
                    event.getChannel().send().message("Command Changed");
                }
            } else {
                String[] s = ArrayUtils.remove(args, 0);
                s = ArrayUtils.remove(s, 0);
                s = ArrayUtils.remove(s, 0);

                String sj = StringUtils.join(s, ' ');
                SimpleMessage c = new SimpleMessage(args[1], Integer.parseInt(args[2]), sj, false);
                GeneralRegistry.SimpleMessage.add(c);
                //          GeneralRegistry.Commands.add(c);
                IRCUtils.RegisterExistingSimpleMessages();
                event.getChannel().send().message("Command Added");

            }
        } else if (args[0].equalsIgnoreCase("a")) {
            if (args[1].startsWith("-")) {
                if (IRCUtils.getCommand(args[1].replaceFirst("-", "")).getPermLevel() <= PermUtils.getPermLevel(event.getBot(), event.getUser(), event.getChannel())) {

                    GeneralRegistry.Commands.remove(IRCUtils.getCommand(args[1].replaceFirst("-", "")));
                    //    GeneralRegistry.SimpleMessage.remove(IRCUtils.getCommand(args[1].replaceFirst("-", "")));
                    event.getChannel().send().message("Command Removed");

                } else {
                    event.getChannel().send().message("Permission Denied");
                }
            } else if (args[1].startsWith("+")) {
                if (IRCUtils.getCommand(args[1].replaceFirst("-", "")).getPermLevel() <= PermUtils.getPermLevel(event.getBot(), event.getUser(), event.getChannel())) {

                    GeneralRegistry.Commands.remove(IRCUtils.getCommand(args[1].replaceFirst("\\+", "")));
                    String[] s = ArrayUtils.remove(args, 0);
                    s = ArrayUtils.remove(s, 0);
                    s = ArrayUtils.remove(s, 0);
                    String sj = StringUtils.join(s, ' ');
                    SimpleAction c = new SimpleAction(args[1].replaceFirst("\\+", ""), Integer.parseInt(args[2]), sj, false);
                    GeneralRegistry.SimpleAction.add(c);
                    //        GeneralRegistry.Commands.add(c);
                    IRCUtils.RegisterExistingSimpleActions();
                    event.getChannel().send().message("Command Changed");

                } else {
                    event.getChannel().send().message("Permission Denied");
                }
            } else {
                String[] s = ArrayUtils.remove(args, 0);
                s = ArrayUtils.remove(s, 0);
                s = ArrayUtils.remove(s, 0);

                String sj = StringUtils.join(s, ' ');
                SimpleAction c = new SimpleAction(args[1], Integer.parseInt(args[2]), sj, false);
                GeneralRegistry.SimpleAction.add(c);
                //        GeneralRegistry.Commands.add(c);
                IRCUtils.RegisterExistingSimpleActions();
                event.getChannel().send().message("Command Added");

            }

        }
    }
}
