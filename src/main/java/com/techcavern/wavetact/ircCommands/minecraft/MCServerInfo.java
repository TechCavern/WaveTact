package com.techcavern.wavetact.ircCommands.minecraft;

import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import io.github.asyncronous.mcping.MCServer;
import io.github.asyncronous.mcping.StandardMCVersions;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.net.InetSocketAddress;

@IRCCMD
@GenCMD
public class MCServerInfo extends IRCCommand {

    public MCServerInfo() {
        super(GeneralUtils.toArray("mcserverinfo mcserver"), 0, "mcserverinfo [address] (port)", "Gets info on minecraft server", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        int port;
        if (args.length >= 2) {
            port = Integer.parseInt(args[1]);
        } else {
            port = 25565;
        }
        MCServer server = StandardMCVersions.MC_18.ping(new InetSocketAddress(GeneralUtils.getIP(args[0], network), port));
        String gameVersion = "Version: " + server.gameVersion;
        String motd = "MOTD: " + server.motd;
        String playercount = "Players: " + Integer.toString(server.players) + "/" + Integer.toString(server.maxPlayers);
        IRCUtils.sendMessage(user, network, channel, args[0] + ":" + port + " - " + gameVersion + " - " + motd + " - " + playercount, prefix);

    }

}

