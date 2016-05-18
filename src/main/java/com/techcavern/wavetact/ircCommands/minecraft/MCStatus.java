package com.techcavern.wavetact.ircCommands.minecraft;

import com.google.gson.JsonArray;
import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@IRCCMD
public class MCStatus extends IRCCommand {

    public MCStatus() {
        super(GeneralUtils.toArray("mcstatus mcs"), 1, null, "Checks status of mojang servers", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        List<String> result = new ArrayList<>();
        JsonArray mcstatus = GeneralUtils.getJsonArray("https://status.mojang.com/check");
        mcstatus.forEach(status -> {
            String name = status.getAsJsonObject().entrySet().iterator().next().getKey().toString();
            if (name.equalsIgnoreCase("minecraft.net")) {
                name = "Website";
            } else if (name.equalsIgnoreCase("api.mojang.com")) {
                name = "API";
            } else if (name.equalsIgnoreCase("authserver.mojang.com")) {
                name = "AuthServer";
            } else if (name.equalsIgnoreCase("sessionserver.mojang.com")) {
                name = "SessionServer";
            } else {
                name = WordUtils.capitalize(name.replace(".minecraft.net", "").replace(".mojang.com", ""));
            }
            String value = status.getAsJsonObject().entrySet().iterator().next().getValue().getAsString();
            if (value.equalsIgnoreCase("green")) {
                value = "Online";
            } else if (value.equalsIgnoreCase("yellow")) {
                value = "Overloaded";
            } else {
                value = "Offline";
            }
            result.add(name + ": " + value);
        });
        if (result != null) {
            IRCUtils.sendMessage(user, network, channel, StringUtils.join(result, " - "), prefix);
        } else {
            IRCUtils.sendError(user, network, channel, "MC status currently unavailable", prefix);
        }
    }

}

