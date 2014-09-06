package com.techcavern.wavetact.commands.minecraft;

import com.google.gson.Gson;
import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import io.github.asyncronous.mcping.MCServer;
import io.github.asyncronous.mcping.StandardMCVersions;
import org.apache.commons.lang3.text.WordUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URL;

@CMD
@GenCMD
public class MCServerInfo extends GenericCommand {

    public MCServerInfo() {
        super(GeneralUtils.toArray("mcserverinfo mcserver"), 0, null,"mcserverinfo [address] (port)");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        int port;
        if(args.length >= 2 ){
            port = Integer.parseInt(args[1]);
        }else{
            port = 25565;
        }
        MCServer server = StandardMCVersions.MC_18.ping(new InetSocketAddress(GeneralUtils.getIP(args[0], Bot), port));
        String gameVersion = "Version: " + server.gameVersion;
        String motd = "MOTD: " + server.motd;
        String playercount = "Players: " +  Integer.toString(server.players) + "/" + Integer.toString(server.maxPlayers);
        IRCUtils.SendMessage(user,channel,args[0] + ":" + port + " - " + gameVersion + " - " + motd + " - " + playercount, isPrivate );

    }

}

