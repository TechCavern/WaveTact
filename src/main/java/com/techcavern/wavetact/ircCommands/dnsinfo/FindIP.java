package com.techcavern.wavetact.ircCommands.dnsinfo;

import com.google.gson.JsonObject;
import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@IRCCMD
public class FindIP extends IRCCommand {

    public FindIP() {
        super(GeneralUtils.toArray("findip locate fip find loc geo geoip"), 1, "findip (+)(IP)(domain)(user)", "GeoIPs a user", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        boolean IPv6Priority = false;
        String nick = user.getNick();
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("+")) {
                IPv6Priority = true;
            } else if (args[0].startsWith("+")) {
                nick = args[0].replaceFirst("\\+", "");
                IPv6Priority = true;
            } else {
                nick = args[0];
            }
        }
        String IP = GeneralUtils.getIP(nick, network, IPv6Priority);
        if (IP == null) {
            IRCUtils.sendError(user, network, channel, "Please enter in an ip/user/domain as argument #1", prefix);
            return;
        }
        JsonObject objectJson = GeneralUtils.getJsonObject("http://ip-api.com/json/" + IP);
        ArrayList<String> results = new ArrayList<>();
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
                IRCUtils.sendMessage(user, network, channel, "[" + IP + "] " + message, prefix);
            } else {
                IRCUtils.sendError(user, network, channel, "Unable to determine location (or you entered an invalid ip)", prefix);
            }
        } else {
            IRCUtils.sendError(user, network, channel, "Unable to determine location (or you entered an invalid ip)", prefix);
        }


    }
}
