/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.techcavern.wavetact.ircCommands.chanhalfop;

import info.techcavern.wavetact.annot.IRCCMD;
import info.techcavern.wavetact.objects.IRCCommand;
import info.techcavern.wavetact.utils.GeneralUtils;
import info.techcavern.wavetact.utils.IRCUtils;
import info.techcavern.wavetact.annot.IRCCMD;
import info.techcavern.wavetact.objects.IRCCommand;
import info.techcavern.wavetact.utils.GeneralUtils;
import info.techcavern.wavetact.utils.IRCUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.List;

/**
 * @author jztech101
 */
@IRCCMD

public class Kick extends IRCCommand {

    public Kick() {
        super(GeneralUtils.toArray("kick"), 7, "kick [user] (message)", "Kicks a user with specified message or none", true);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        String message = "[" + user.getNick() + "]";
        if (args.length > 1) {
            message += " " + GeneralUtils.buildMessage(1, args.length, args);
        }else{
            message += " Your behavior is not conductive to the desired environment";
        }
        if (args[0].contains(",")){
            String[] nicks = StringUtils.split(args[0], ",");
            for(String nick:nicks){
                kick(network, user, channel, message, nick);
            }
        }else{
            kick(network, user, channel, message, args[0]);
        }
    }
    static void kick(PircBotX network, User user, Channel channel, String message, String nick){
        if (nick.equalsIgnoreCase(network.getNick()))
            IRCUtils.sendKick(network.getUserBot(), user, network, channel, message);
        else
            IRCUtils.sendKick(network.getUserBot(), IRCUtils.getUserByNick(network, nick), network, channel, message);
    }
    }



