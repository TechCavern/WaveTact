package com.techcavern.wavetact.commands.utils;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.utils.*;
import com.techcavern.wavetact.utils.objects.ChannelUserProperty;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import com.techcavern.wavetact.utils.objects.PermChannel;
import org.apache.commons.lang3.ArrayUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

/**
 * Created by jztech101 on 6/23/14.
 */

@SuppressWarnings("ALL")
@CMD
@GenCMD
public class ListRelayBots extends GenericCommand {

    public ListRelayBots() {
        super(GeneralUtils.toArray("listrelaybots listrelays"), 0, "listrelays", "Returns list of relay bots", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        Channel chan;
        if (args.length > 1 && network.getServerInfo().getChannelTypes().contains(String.valueOf(args[0].charAt(0)))) {
            chan = GetUtils.getChannelbyName(network, args[0]);
            args = ArrayUtils.remove(args, 0);
        }else{
            chan = channel;
        }
            String relaylist = "";
            for(ChannelUserProperty relay : Registry.RelayBots){
                if(relay.getChannelName().equals(chan.getName()) && relay.getNetworkName().equals(GetUtils.getNetworkNameByNetwork(network))) {
                    if (relaylist.isEmpty())
                        relaylist = relay.getUser();
                    else
                        relaylist += ", " + relay.getUser();
                }
            }
        if(relaylist.isEmpty()){
            ErrorUtils.sendError(user, "No relays found");
        }else{
            IRCUtils.sendMessage(user, network, channel,relaylist,prefix);
        }
    }
}

