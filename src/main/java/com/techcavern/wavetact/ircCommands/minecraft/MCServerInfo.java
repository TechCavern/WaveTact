package com.techcavern.wavetact.ircCommands.minecraft;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.xbill.DNS.*;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

@IRCCMD
public class MCServerInfo extends IRCCommand {

    public MCServerInfo() {
        super(GeneralUtils.toArray("mcserverinfo mcserver mcping"), 0, "mcserverinfo [address] (port)", "Gets info on minecraft server", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
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
        InetSocketAddress address = new InetSocketAddress(GeneralUtils.getIP(args[0], network, false), port);
        Socket socket = new Socket();
            socket.connect(address, 10000);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                DataInputStream in = new DataInputStream(socket.getInputStream());
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                DataOutputStream handshake = new DataOutputStream(bos);
                handshake.writeByte(0x00);
                GeneralUtils.writeOutputStream(handshake, 4);
                GeneralUtils.writeOutputStream(handshake, address.getHostString().length());
                handshake.writeBytes(address.getHostString());
                handshake.writeShort(address.getPort());
                GeneralUtils.writeOutputStream(handshake, 1);
                GeneralUtils.writeOutputStream(out, bos.size());
                out.write(bos.toByteArray());
                out.writeByte(0x01);
                out.writeByte(0x00);
                GeneralUtils.readInputStream(in);
                int id = GeneralUtils.readInputStream(in);
                if (id != 0x00) {
                    ErrorUtils.sendError(user, "Error parsing packet response");
                    return;
                }
                int length = GeneralUtils.readInputStream(in);
                if (length == 0 || length == -1) {
                    ErrorUtils.sendError(user, "Error parsing packet response");
                    return;
                }
                byte[] bits = new byte[length];
                in.readFully(bits);
                JsonObject response = new JsonParser().parse(new String(bits)).getAsJsonObject();
        String gameVersion = "Version: " + response.get("version").getAsJsonObject().get("name").getAsString();
        String motd = "MOTD: " + response.get("description").getAsString();
        String playercount = "Players: " + response.get("players").getAsJsonObject().get("online").getAsString() + "/" + response.get("players").getAsJsonObject().get("max").getAsString();
        IRCUtils.sendMessage(user, network, channel, args[0] + ":" + port + " - " + gameVersion + " - " + motd + " - " + playercount, prefix);
    }

}

