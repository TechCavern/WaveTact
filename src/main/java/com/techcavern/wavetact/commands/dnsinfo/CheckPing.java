package com.techcavern.wavetact.commands.dnsinfo;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

@CMD
@GenCMD
public class CheckPing extends GenericCommand {

    public CheckPing() {
        super(GeneralUtils.toArray("checkping cping"), 0, "checkport [ip][domain]", " Checks ping to a server", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        String IP = GeneralUtils.getIP(args[0], network);
        if(IP == null){
            IRCUtils.sendMessage(user, network, channel, "Host Unreachable", prefix);
        }else{
            long time = System.currentTimeMillis();
            if(InetAddress.getByName(IP).isReachable(2000)) {
                IRCUtils.sendMessage(user, network, channel, IP + ": " + (System.currentTimeMillis() - time) + " milliseconds", prefix);
            }else{
                IRCUtils.sendMessage(user, network, channel, "Host Unreachable", prefix);
            }
        }

    }
}