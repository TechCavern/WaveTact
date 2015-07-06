package com.techcavern.wavetact.ircCommands.minecraft;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.objects.MCMod;
import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.ArrayList;
import java.util.List;

@IRCCMD
public class MCModStats extends IRCCommand {

    public MCModStats() {
        super(GeneralUtils.toArray("mcmodstats mcms"), 0, "mcmodstats", "Gets mod stats, ignores MC Versions with less than 10 mods", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        JsonArray versions = GeneralUtils.getJsonArray("http://bot.notenoughmods.com/?json");
        List<String> mcModStats = new ArrayList<>();
        for (int i = versions.size() - 1; i > 0; i--) {
            JsonArray mods = GeneralUtils.getJsonArray("http://bot.notenoughmods.com/" + versions.get(i).getAsString() + ".json");
            if (mods.size() > 10)
                mcModStats.add(versions.get(i).getAsString() + ": " + mods.size() + " mods");
        }
        IRCUtils.sendMessage(user, network, channel, StringUtils.join(mcModStats, ", "), prefix);
    }
}


