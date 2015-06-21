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

@IRCCMD
public class MCMods extends IRCCommand {

    public MCMods() {
        super(GeneralUtils.toArray("mcmods mcmod"), 0, "mcmods (mc version#) (+)[mod name]", "Gets info on a minecraft mod", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        JsonArray versions = GeneralUtils.getJsonArray("http://bot.notenoughmods.com/?json");
        boolean isDev = false;
        String version = "";
        String modname = "";
        JsonArray mods = null;
        for (int i = 0; i < versions.size(); i++) {
            String vers = versions.get(i).getAsString();
            if (vers.contains(args[0])) {
                version = vers;
                modname = args[1].toLowerCase();
            }
        }
        if (version.isEmpty()) {
            int arraysize = 0;
            int versionsize = versions.size();
            while (arraysize <= 100) {
                versionsize = versionsize - 1;
                version = versions.get(versionsize).getAsString();
                mods = GeneralUtils.getJsonArray("http://bot.notenoughmods.com/" + version + ".json");
                arraysize = mods.size();
            }
            modname = args[0].toLowerCase();
        } else {
            mods = GeneralUtils.getJsonArray("http://bot.notenoughmods.com/" + version + ".json");
        }
        if (modname.startsWith("+")) {
            isDev = true;
            modname = modname.replaceFirst("\\+", "");
        }
        int total = 0;
        for (int i = 0; i < mods.size(); i++) {
            JsonObject mod = mods.get(i).getAsJsonObject();
            if (mod.get("name").getAsString().toLowerCase().contains(modname)) {
                String ModVersion = "";
                if (isDev)
                    ModVersion = mod.get("dev").getAsString();
                else
                    ModVersion = mod.get("version").getAsString();
                String Name = mod.get("name").getAsString();
                String Link = mod.get("shorturl").getAsString();
                String Author = mod.get("author").getAsString();
                if (Link.isEmpty()) {
                    Link = mod.get("longurl").getAsString();
                }
                if (total < 3) {
                    if (!ModVersion.isEmpty()) {
                        IRCUtils.sendMessage(user, network, channel, "[" + ModVersion + "] " + Name + " by " + Author + " - " + Link, prefix);
                        total++;
                    }
                }
            }
        }
        if (total == 0) {
            ErrorUtils.sendError(user, "No mods found");
        }
    }
}


