package com.techcavern.wavetact.ircCommands.dnsinfo;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.objects.IRCCommand;
import org.apache.http.conn.util.InetAddressUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@IRCCMD
@GenCMD
public class CheckPing extends IRCCommand {

    public CheckPing() {
        super(GeneralUtils.toArray("checkping cping"), 0, "checkport [ip][domain]", " Checks ping to a server", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        String IP = GeneralUtils.getIP(args[0], network);
        if(IP == null){
            ErrorUtils.sendError(user, "Host Unreachable");
        }else{
            String command = "";
            if(System.getProperty("os.name").startsWith("Windows")) {
                command = "ping -n 1 " + IP;
            }else if(InetAddressUtils.isIPv6Address(IP)){
                command = "ping6 -c 1 " + IP;
            }else if(InetAddressUtils.isIPv4Address(IP)){
                command = "ping -c 1 " + IP;
            }
            long time = System.currentTimeMillis();
            Process pinghost = Runtime.getRuntime().exec(command);
            pinghost.waitFor();
            if(pinghost.exitValue() == 0){
                IRCUtils.sendMessage(user, network, channel, IP + ": " + (System.currentTimeMillis() - time) + " milliseconds", prefix);
            }else{
                ErrorUtils.sendError(user, "Host Unreachable");
            }
        }

    }
}