package com.techcavern.wavetact.commands.utils;

import com.google.gson.JsonObject;
import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.ArrayList;
import java.util.List;

@CMD
@GenCMD
public class FindIP extends GenericCommand {

    public FindIP() {
        super(GeneralUtils.toArray("findip locate find loc geo geoip"), 0, "findip [IP][domain][user]", "GeoIPs a user - IPv6 NOT supported");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        String IP;
        if (args.length > 0) {
            if (args[0].contains(".") || args[0].contains(":")) {
                IP = args[0].replace("http://", "").replace("https://", "");
            } else {
                IP = IRCUtils.getHost(Bot, args[0]);
            }
        } else {
            IP = IRCUtils.getHost(Bot, user.getNick());
        }
        if (IP == null) {
            IRCUtils.sendError(user, "Please Enter in an IP/User/Domain as argument #1");
            return;
        }

        JsonObject objectJson = GeneralUtils.getJsonObject("http://ip-api.com/json/" + IP);
        List<String> results = new ArrayList<>();
        if (objectJson.get("status").getAsString().equalsIgnoreCase("success")) {
            results.add(objectJson.get("city").getAsString());
            results.add(objectJson.get("regionName").getAsString());
            results.add(objectJson.get("country").getAsString());
            results.add(objectJson.get("zip").getAsString());
            results.add(objectJson.get("isp").getAsString());
            results.add(objectJson.get("timezone").getAsString());
            String fin = "";
            for (String res : results) {
                if (!res.isEmpty()) {
                    if (fin.isEmpty()) {
                        fin = res;
                    } else {
                        fin += ", " + res;
                    }
                }
            }
            if (!fin.isEmpty()) {
                IRCUtils.SendMessage(user, channel, fin, isPrivate);
            } else {
                IRCUtils.sendError(user, "Unable to Determine Location (Or you entered an invalid IP)");
            }
        } else {
            IRCUtils.sendError(user, "Unable to Determine Location (Or you entered an invalid IP)");
        }


    }
}
