package com.techcavern.wavetact.commands.dnsinfo;

import com.google.gson.JsonObject;
import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.utils.ErrorUtils;
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
        super(GeneralUtils.toArray("findip locate find loc geo geoip"), 0, "findip [IP][domain][user]", "GeoIPs a user", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        String IP;
        if (args.length > 0) {
            if (args[0].contains(".") || args[0].contains(":")) {
                IP = args[0].replace("http://", "").replace("https://", "");
            } else {
                IP = IRCUtils.getHost(network, args[0]);
            }
        } else {
            IP = IRCUtils.getHost(network, user.getNick());
        }
        if (IP == null) {
            ErrorUtils.sendError(user, "Please Enter in an IP/User/Domain as argument #1");
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
            String message = "";
            for (String res : results) {
                if (!res.isEmpty()) {
                    if (message.isEmpty()) {
                        message = res;
                    } else {
                        message += ", " + res;
                    }
                }
            }
            if (!message.isEmpty()) {
                IRCUtils.sendMessage(user, network, channel, message, prefix);
            } else {
                ErrorUtils.sendError(user, "Unable to Determine Location (Or you entered an invalid IP)");
            }
        } else {
            ErrorUtils.sendError(user, "Unable to Determine Location (Or you entered an invalid IP)");
        }


    }
}
