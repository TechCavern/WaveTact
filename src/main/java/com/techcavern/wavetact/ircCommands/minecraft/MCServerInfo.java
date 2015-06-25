package com.techcavern.wavetact.ircCommands.minecraft;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import io.github.asyncronous.mcping.MCServer;
import io.github.asyncronous.mcping.StandardMCVersions;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.xbill.DNS.*;

import java.net.InetSocketAddress;

@IRCCMD
public class MCServerInfo extends IRCCommand {

    public MCServerInfo() {
        super(GeneralUtils.toArray("mcserverinfo mcserver mcping"), 0, "mcserverinfo [address] (port)", "Gets info on minecraft server", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        int port = 25565;
        boolean isSuccessful = false;
        if (args.length >= 2) {
            port = Integer.parseInt(args[1]);
        } else {
            Resolver resolver = new SimpleResolver();
            Lookup lookup = new Lookup(args[0], Type.ANY);
            lookup.setResolver(resolver);
            lookup.setCache(null);
            Record[] records = lookup.run();
            if(lookup.getResult() == Lookup.SUCCESSFUL) {
                int Priority = 0;
                for (Record rec : records) {
                    if (rec instanceof SRVRecord) {
                        if(isSuccessful && ((SRVRecord) rec).getPriority() < Priority){
                            args[0] = ((SRVRecord) rec).getTarget().toString();
                            port = ((SRVRecord) rec).getPort();
                            Priority = ((SRVRecord) rec).getPriority();
                        }else{
                            args[0] = ((SRVRecord) rec).getTarget().toString();
                            port = ((SRVRecord) rec).getPort();
                        }
                        isSuccessful = true;

                    }
                }
            }
        }
        MCServer server = StandardMCVersions.MC_18.ping(new InetSocketAddress(GeneralUtils.getIP(args[0], network, false), port));
        String gameVersion = "Version: " + server.gameVersion;
        String motd = "MOTD: " + server.motd;
        String playercount = "Players: " + Integer.toString(server.players) + "/" + Integer.toString(server.maxPlayers);
        IRCUtils.sendMessage(user, network, channel, args[0] + ":" + port + " - " + gameVersion + " - " + motd + " - " + playercount, prefix);

    }

}

