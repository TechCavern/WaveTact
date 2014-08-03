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
        super(GeneralUtils.toArray("findip locate find loc geo geoip"), 0, "findip [ipv4][domain][user]", "GeoIPs a user - IPv6 NOT supported");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        String IP = GeneralUtils.getIP(args[0], Bot);
        if(IP == null){
            user.send().notice("Invalid IP/User");
            return;
        }
            JsonObject objectJson = GeneralUtils.getJsonObject("http://freegeoip.net/json/" + IP);
            String location = objectJson.get("city").getAsString() + ", " + objectJson.get("region_name").getAsString() + ", " + objectJson.get("zipcode").getAsString();
            if (location.equalsIgnoreCase(", , ")) {
                user.send().notice("IP is Protected");
            } else {
                IRCUtils.SendMessage(user, channel, location, isPrivate);
            }
    }
}
