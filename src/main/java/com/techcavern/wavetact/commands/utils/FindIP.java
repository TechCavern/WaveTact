package com.techcavern.wavetact.commands.utils;

import com.google.gson.JsonObject;
import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.net.InetAddress;
import java.net.Socket;


public class FindIP extends GenericCommand {
    @CMD
    @GenCMD

    public FindIP() {
        super(GeneralUtils.toArray("findip locate find loc geo geoip"), 0, "findip [ipv4][domain][user]","GeoIPs a user - IPv6 NOT supported");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        String IP = "";
        if (args[0].contains(".") || args[0].contains(":")) {
            IP = args[0];
        } else {
            IP = IRCUtils.getHost(Bot, args[0]);
        }
        if(IP.replaceFirst(":", "").contains(":")){
            user.send().notice("IPv6 is not supported");
            return;
        }
        Long time = System.currentTimeMillis();
        IP = IP.replaceAll("http://|https://", "");
        Socket socket = new Socket(InetAddress.getByName(IP), 80);
        IP = socket.getInetAddress().getHostAddress();
        socket.close();
        if(IP.contains(":")){
            user.send().notice("IPv6 is not supported");
        }
        JsonObject objectJson = GeneralUtils.getJsonObject("http://freegeoip.net/json/" + IP);
        String location = objectJson.get("city").getAsString() + ", " + objectJson.get("region_name").getAsString() + ", " + objectJson.get("zipcode").getAsString();
        if (location.equalsIgnoreCase(", , ")) {
            user.send().notice("IP is Protected");
        }
    else{
            IRCUtils.SendMessage(user, channel, location, isPrivate);
        }

    }
}
