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
public class Roll extends IRCCommand {

    public Roll() {
        super(GeneralUtils.toArray("rolldice roll dice"), 0, "rolldice (times) [x]d[y]", "rolls some dice", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        int x = 1;
        try{
            x = Integer.parseInt(args[0]);
            args = ArrayUtils.remove(args,0);
        }catch (NumberFormatException e){
        }
        if(x > 50){
            IRCUtils.sendError(user, network, channel, "You may not roll the dice more than 50 times", prefix);
            return;
        }else if(x <= 0){
            IRCUtils.sendError(user, network, channel, "I cannot roll the dice that amount of times", prefix);
            IRCUtils.sendError(user, network, channel, "I cannot roll the dice that amount of times", prefix);
            return;
        }
        String[] dice = StringUtils.split(args[0],"d");
        if(Integer.parseInt(dice[0]) > 100){
            IRCUtils.sendError(user, network, channel, "You may not roll more than 100 dice at a time", prefix);
        }else if(Integer.parseInt(dice[1]) > 100){
            IRCUtils.sendError(user, network, channel, "You may not roll more than 100 sided dice", prefix);
        }else if(Integer.parseInt(dice[0])  <= 0){
            IRCUtils.sendError(user, network, channel, "I cannot find that amount of dice", prefix);
        }else if(Integer.parseInt(dice[1]) <= 0){
            IRCUtils.sendError(user, network, channel, "I cannot find dice with that amount of sides", prefix);
        }else{
            List<Integer> list = new ArrayList<>();
            for(int i = 0; i<x; i++){
                int b = 0;
                for(int j = 0; j<Integer.parseInt(dice[0]); j++) {
                    {
                        b += Registry.randNum.nextInt(Integer.parseInt(dice[1]+1));
                    }
                }
                list.add(b);
            }
            IRCUtils.sendMessage(user, network, channel, StringUtils.join(list, ", "), prefix);
        }
    }
}
