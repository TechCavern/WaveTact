/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.ircCommands.fun;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.Registry;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jztech101
 */
@IRCCMD
public class RockPaperScissors extends IRCCommand {

    public RockPaperScissors() {
        super(GeneralUtils.toArray("rockpaperscissors rps rock paper scissor scissors rockpaperscissor"), 0, "rockpaperscissors [input]", "plays rock paper scissors", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
       int x = Registry.randNum.nextInt(3); //0 = rock, 1 = scissors, 2=paper
        if(x == 0 && args[0].equalsIgnoreCase("paper")){
            IRCUtils.sendMessage(user, network, channel, "Your paper wraps around my rock, you win!", prefix);
        }else if(x == 0 && args[0].equalsIgnoreCase("rock")){
            IRCUtils.sendMessage(user, network, channel, "My rock is bigger and crushes your rock, you lose! ", prefix);
        }else if(x == 0 && (args[0].equalsIgnoreCase("scissor")||args[0].equalsIgnoreCase("scissors"))){
            IRCUtils.sendMessage(user, network, channel, "My rock crushes your scissors, you lose! ", prefix);
        }else if(x == 1 && args[0].equalsIgnoreCase("paper")){
            IRCUtils.sendMessage(user, network, channel, "My scissors cuts your paper, you lose! ", prefix);
        }else if(x == 1 && args[0].equalsIgnoreCase("rock")){
            IRCUtils.sendMessage(user, network, channel, "Your rock crushes my scissors, you win! ", prefix);
        }else if(x == 1 && (args[0].equalsIgnoreCase("scissor")||args[0].equalsIgnoreCase("scissors"))){
            IRCUtils.sendMessage(user, network, channel, "My scissors are bigger and cuts your scissors in half, you lose! ", prefix);
        }else if(x == 2 && args[0].equalsIgnoreCase("paper")){
            IRCUtils.sendMessage(user, network, channel, "My paper is thicker and crushes your paper, you lose! ", prefix);
        }else if(x == 2 && args[0].equalsIgnoreCase("rock")){
            IRCUtils.sendMessage(user, network, channel, "My paper wraps around your rock, you lose! ", prefix);
        }else if(x == 2 && (args[0].equalsIgnoreCase("scissor")||args[0].equalsIgnoreCase("scissors"))) {
            IRCUtils.sendMessage(user, network, channel, "Your scissors cuts my paper, you win! ", prefix);
        }else{
            IRCUtils.sendError(user, network, channel, "I'm a big bad bot that crushes your " + args[0], prefix);
        }
    }
}
