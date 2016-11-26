/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.ircCommands.chanhalfop;

import com.google.common.collect.ImmutableSortedSet;
import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.*;


/**
 * @author jztech101
 */
@IRCCMD

public class Voice extends IRCCommand {

    public Voice() {
        super(GeneralUtils.toArray("voice vop devoice devop"), 7, "voice (user)", "Voices a user", true);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        String nick = user.getNick();
        if (args.length >= 1) {
            nick = args[0];
        }
        if(nick.equalsIgnoreCase("-all")){
            Set<String> names = new HashSet<>();
            List<String> nicks = new ArrayList<>(Collections.unmodifiableList(channel.getUsersNicks().asList()));
            nicks.remove(network.getNick());
            for(int i = 0; i<nicks.size(); i+=4){
                String thing = "";
                for(int j = i; j<i+4;j++){
                    try {
                        if (!thing.isEmpty())
                            thing = nicks.get(j) + " " + thing;
                        else
                            thing = nicks.get(j);
                    }catch (IndexOutOfBoundsException e){
                        break;
                    }
                }
                names.add(thing);
            }
            for(String thing:names){
                if (command.contains("de")) {
                    IRCUtils.setMode(channel, network, "-vvvv", thing);
                } else {
                    IRCUtils.setMode(channel, network, "+vvvv", thing);
                }
            }
        }else {
            if (command.contains("de")) {
                IRCUtils.setMode(channel, network, "-v", nick);
            } else {
                IRCUtils.setMode(channel, network, "+v", nick);
            }
        }
    }
}
