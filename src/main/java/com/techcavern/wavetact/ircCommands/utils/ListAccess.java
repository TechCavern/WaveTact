package com.techcavern.wavetact.ircCommands.utils;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.utils.*;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.objects.PermChannel;
import org.apache.commons.lang3.ArrayUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

/**
 * Created by jztech101 on 6/23/14.
 */

@SuppressWarnings("ALL")
@IRCCMD
@GenCMD
public class ListAccess extends IRCCommand {

    public ListAccess() {
        super(GeneralUtils.toArray("listaccess listaccesslist laccess"), 0, "listaccess [permlevel/all]", "Returns list of users specific to that permlevel or all users", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        boolean hasUsers = false;
        Channel chan;
        if (args.length > 1 && network.getServerInfo().getChannelTypes().contains(String.valueOf(args[0].charAt(0)))) {
            chan = GetUtils.getChannelbyName(network, args[0]);
            args = ArrayUtils.remove(args, 0);
        }else{
            chan = channel;
        }
        try{
            int accesslevel = Integer.parseInt(args[0]);
            for(PermChannel acc : Registry.PermChannels){
                if(acc.getChannelName().equals(chan.getName())&& acc.getPermLevel() == accesslevel && acc.getNetworkName().equals(GetUtils.getNetworkNameByNetwork(network))){
                    IRCUtils.sendMessage(user,network,channel,acc.getPermUser() + " " + acc.getPermLevel(), prefix);
                    hasUsers = true;
                }
            }
        }catch(NumberFormatException|ArrayIndexOutOfBoundsException e){
            for(PermChannel acc : Registry.PermChannels){
                if(acc.getChannelName().equals(chan.getName()) && acc.getNetworkName().equals(GetUtils.getNetworkNameByNetwork(network))){
                    IRCUtils.sendMessage(user,network,channel,acc.getPermUser() + " " + acc.getPermLevel(), prefix);
                    hasUsers = true;
                }
            }
        }
        if(!hasUsers){
            ErrorUtils.sendError(user, "No users found on access lists with specified criteria");
        }
    }
}

