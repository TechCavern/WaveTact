package com.techcavern.wavetact.ircCommands.minecraft;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@IRCCMD
public class MCMods extends IRCCommand {

    public MCMods() {
        super(GeneralUtils.toArray("mcmods mcmod mcmodauthor mcma mcm"), 0, "mcmods (+)[mod name]", "Gets info on minecraft mods", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        JsonArray versions = GeneralUtils.getJsonArray("http://bot.notenoughmods.com/?json");
        boolean isDev = false;
        String modname = args[0].toLowerCase();
        Map<JsonObject, String> mcmods = new HashMap<>();
        if (modname.startsWith("+")) {
            isDev = true;
            modname = modname.replaceFirst("\\+", "");
        }
        String searchPhrase = "name";
        if (command.equalsIgnoreCase("mcmodauthor") || command.equalsIgnoreCase("mcma"))
            searchPhrase = "author";
        MCModVersionSearch:
        for (int i = versions.size() - 1; i > 0; i--) {
            JsonArray mods = GeneralUtils.getJsonArray("http://bot.notenoughmods.com/" + versions.get(i).getAsString() + ".json");
            MCModSearch:
            for (int j = 0; j < mods.size(); j++) {
                JsonObject mod = mods.get(j).getAsJsonObject();
                if (mcmods.size() >= 3) {
                    break MCModVersionSearch;
                }
                Iterator iterator = mcmods.keySet().iterator();
                while (iterator.hasNext()) {
                    if (mod.get("name").getAsString().equalsIgnoreCase(((JsonObject) iterator.next()).get("name").getAsString()))
                        continue MCModSearch;
                }
                if (mod.get(searchPhrase).getAsString().toLowerCase().contains(modname)) {
                    mcmods.put(mod, versions.get(i).getAsString());
                }
            }
        }
        if (mcmods.isEmpty()) {
            ErrorUtils.sendError(user, "No mods found");
        } else {
            Iterator iterator = mcmods.keySet().iterator();
            while (iterator.hasNext()) {
                JsonObject mod = (JsonObject) iterator.next();
                String ModVersion = "";
                if (isDev)
                    ModVersion = mod.get("dev").getAsString();
                else
                    ModVersion = mod.get("version").getAsString();
                String Name = mod.get("name").getAsString();
                String Link = mod.get("shorturl").getAsString();
                String Author = mod.get("author").getAsString();
                if (Author.isEmpty())
                    IRCUtils.sendMessage(user, network, channel, "[" + mcmods.get(mod) + "] " + Name + " " + ModVersion + " - " + Link, prefix);
                else
                    IRCUtils.sendMessage(user, network, channel, "[" + mcmods.get(mod) + "] " + Name + " " + ModVersion + " by " + Author + " - " + Link, prefix);
            }
        }
    }
}


