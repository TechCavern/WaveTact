/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands;

import com.techcavern.wavetact.utils.Command;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.PermUtils;
import com.techcavern.wavetact.utils.SimpleMessage;
import org.pircbotx.hooks.events.MessageEvent;

/**
 *
 * @author jztech101
 */
public class CustomCMD extends Command{
    public CustomCMD(){
        super("customcmd", 0);
    }
    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception{
        if(args[0].equalsIgnoreCase("m")){
            if(args[1].startsWith("-")){
                if(IRCUtils.getCommand(args[1].replaceFirst("-", "")).getPermLevel() <= PermUtils.getPermLevel(event.getBot(), event.getUser(), event.getChannel())){
                GeneralRegistry.Commands.remove(IRCUtils.getCommand(args[1].replaceFirst("-", "")));
                GeneralRegistry.SimpleMessage.remove(IRCUtils.getCommand(args[1].replaceFirst("-", "")));
                }else{
                    event.getChannel().send().message("Permission Denied");
                }
            }else{
                SimpleMessage c = new SimpleMessage(args[1], Integer.parseInt(args[2]), args[3], false);
                GeneralRegistry.SimpleMessage.add(c);
                GeneralRegistry.Commands.add(c);
            }
            }
        else if(args[0].equalsIgnoreCase("a")){
            if(args[1].startsWith("-")){
               if(IRCUtils.getCommand(args[1].replaceFirst("-", "")).getPermLevel() <= PermUtils.getPermLevel(event.getBot(), event.getUser(), event.getChannel())){

                GeneralRegistry.Commands.remove(IRCUtils.getCommand(args[1].replaceFirst("-", "")));
                GeneralRegistry.SimpleMessage.remove(IRCUtils.getCommand(args[1].replaceFirst("-", "")));
                }else{
                    event.getChannel().send().message("Permission Denied");
                }
            }else{
                SimpleMessage c = new SimpleMessage(args[1], Integer.parseInt(args[2]), args[3], false);
                GeneralRegistry.SimpleMessage.add(c);
                GeneralRegistry.Commands.add(c);
            }
            }
        }
    }

