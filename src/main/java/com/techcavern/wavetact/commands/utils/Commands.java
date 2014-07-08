package com.techcavern.wavetact.commands.utils;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.PermUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GeneralUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jztech101 on 6/23/14.
 */

@SuppressWarnings("ALL")
public class Commands extends GenericCommand {
    @CMD
    public Commands() {
        super(GeneralUtils.toArray("commands list cmds coms"), 0, "Takes 0 arguments, returns list of Commands");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, String... args) throws Exception {
        List<String> GenericCommands = new ArrayList<String>();
        List<String>  TrustedCommands = new ArrayList<String>();
        List<String>  ControllerCommands = new ArrayList<String>();
        List<String>  ChanOwnerCommands = new ArrayList<String>();
        List<String>  ChanHalfOpCommands = new ArrayList<String>();
        List<String>  ChanOpCommands = new ArrayList<String>();
        List<String>  ChanFounderCommands = new ArrayList<String>();

        for(GenericCommand command : GeneralRegistry.GenericCommands){
          GenericCommands.add(command.getCommand());
          TrustedCommands.add(command.getCommand());
          ChanHalfOpCommands.add(command.getCommand());
          ChanOpCommands.add(command.getCommand());
          ChanOwnerCommands.add(command.getCommand());
          ChanFounderCommands.add(command.getCommand());
          ControllerCommands.add(command.getCommand());
        }
        for(GenericCommand command : GeneralRegistry.TrustedCommands){
            TrustedCommands.add(command.getCommand());
            ChanHalfOpCommands.add(command.getCommand());
            ChanOpCommands.add(command.getCommand());
            ChanOwnerCommands.add(command.getCommand());
            ChanFounderCommands.add(command.getCommand());
            ControllerCommands.add(command.getCommand());
        }
        for(GenericCommand command : GeneralRegistry.AnonymonityCommands){
            TrustedCommands.add(command.getCommand());
            ChanHalfOpCommands.add(command.getCommand());
            ChanOpCommands.add(command.getCommand());
            ChanOwnerCommands.add(command.getCommand());
            ChanFounderCommands.add(command.getCommand());
            ControllerCommands.add(command.getCommand());
        }
        for(GenericCommand command : GeneralRegistry.ChanHalfOpCommands){
            ChanHalfOpCommands.add(command.getCommand());
            ChanOpCommands.add(command.getCommand());
            ChanOwnerCommands.add(command.getCommand());
            ChanFounderCommands.add(command.getCommand());
            ControllerCommands.add(command.getCommand());
        }
        for(GenericCommand command : GeneralRegistry.ChanOpCommands){
            ChanOpCommands.add(command.getCommand());
            ChanOwnerCommands.add(command.getCommand());
            ChanFounderCommands.add(command.getCommand());
            ControllerCommands.add(command.getCommand());
        }
        for(GenericCommand command : GeneralRegistry.ChanOwnerCommands){
            ChanOwnerCommands.add(command.getCommand());
            ChanFounderCommands.add(command.getCommand());
            ControllerCommands.add(command.getCommand());
        }
        for(GenericCommand command : GeneralRegistry.ChanFounderCommands){
            ChanFounderCommands.add(command.getCommand());
            ControllerCommands.add(command.getCommand());
        }
        for(GenericCommand command : GeneralRegistry.ControllerCommands){
            ControllerCommands.add(command.getCommand());
        }


        int i = PermUtils.getPermLevel(Bot, user, channel);
        if (i == 9001) {
            IRCUtils.SendMessage(user, channel, StringUtils.join(ControllerCommands, ", "), isPrivate);
        } else if (i == 20) {
            IRCUtils.SendMessage(user, channel, StringUtils.join(ChanFounderCommands, ", "), isPrivate);
        }else if (i == 18) {
            IRCUtils.SendMessage(user, channel, StringUtils.join(ChanFounderCommands, ", "), isPrivate);
        }else if (i == 15) {
            IRCUtils.SendMessage(user, channel, StringUtils.join(ChanOwnerCommands, ", "), isPrivate);
        }else if (i == 13) {
            IRCUtils.SendMessage(user, channel, StringUtils.join(ChanOpCommands, ", "), isPrivate);
        } else if (i == 10) {
            IRCUtils.SendMessage(user, channel, StringUtils.join(ChanOpCommands, ", "), isPrivate);
        } else if (i == 7) {
            IRCUtils.SendMessage(user, channel, StringUtils.join(ChanHalfOpCommands, ", "), isPrivate);
        } else if (i == 5) {
            IRCUtils.SendMessage(user, channel, StringUtils.join(TrustedCommands, ", "), isPrivate);
        } else {
            IRCUtils.SendMessage(user, channel, StringUtils.join(GenericCommands, ", "), isPrivate);
        }

    }
}

