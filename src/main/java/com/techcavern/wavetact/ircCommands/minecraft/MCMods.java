package com.techcavern.wavetact.ircCommands.minecraft;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.HashMap;
import java.util.Map;

@IRCCMD
public class MCMods extends IRCCommand {

    public MCMods() {
        super(GeneralUtils.toArray("mcmods mcmod mcmodauthor mcma mcm"), 0, "mcmods (+)[mod name]", "Gets info on minecraft mods", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        JsonArray versions = GeneralUtils.getJsonArray("http://bot.notenoughmods.com/?json&count");
        boolean isDev = false;
        String modname = StringUtils.join(args).toLowerCase().replaceAll("[^a-zA-Z0-9]", "");
        Map<JsonObject, String> mcmods = new HashMap<>();
        if (modname.startsWith("+")) {
            isDev = true;
            modname = modname.replaceFirst("\\+", "");
        }
        String searchPhrase = "name";
        if (command.equalsIgnoreCase("mcmodauthor") || command.equalsIgnoreCase("mcma"))
            searchPhrase = "author";
        MCModVersionSearch:
        for (int i = versions.size() - 1; i >= 0; i--) {
            int size = versions.get(i).getAsJsonObject().get("count").getAsInt();
            if (size > 10) {
                String version = versions.get(i).getAsJsonObject().get("name").getAsString();
                JsonArray mods = GeneralUtils.getJsonArray("http://bot.notenoughmods.com/" + version + ".json");
                MCModSearch:
                for (int j = 0; j < mods.size(); j++) {
                    JsonObject mod = mods.get(j).getAsJsonObject();
                    if (mcmods.size() >= 3) {
                        break MCModVersionSearch;
                    }
                    for (JsonObject o : mcmods.keySet()) {
                        if (mod.get("name").getAsString().equalsIgnoreCase((o).get("name").getAsString()))
                            continue MCModSearch;
                    }
                    if (mod.get(searchPhrase).getAsString().toLowerCase().replaceAll("[^a-zA-Z0-9]", "").contains(modname)) {
                        mcmods.put(mod, version);
                    }
                }
            }
        }
        if (mcmods.isEmpty()) {
            IRCUtils.sendError(user, network, channel, "No mods found", prefix);
        } else {
            for (JsonObject mod : mcmods.keySet()) {
                String ModVersion;
                if (isDev)
                    ModVersion = mod.get("dev").getAsString();
                else
                    ModVersion = mod.get("version").getAsString();
                String Name = mod.get("name").getAsString();
                String Link = mod.get("shorturl").getAsString();
                if (Link.isEmpty())
                    Link = mod.get("longurl").getAsString();
                String Author = mod.get("author").getAsString();
                if (Author.isEmpty())
                    IRCUtils.sendMessage(user, network, channel, "[" + mcmods.get(mod) + "] " + Name + " " + ModVersion + " - " + Link, prefix);
                else
                    IRCUtils.sendMessage(user, network, channel, "[" + mcmods.get(mod) + "] " + Name + " " + ModVersion + " by " + Author + " - " + Link, prefix);
            }
        }
    }
}


