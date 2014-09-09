package com.techcavern.wavetact.commands.minecraft;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@CMD
@GenCMD
public class MCMods extends GenericCommand {

    public MCMods() {
        super(GeneralUtils.toArray("mcmods mcmod"), 0, "mcmods (MC Version#) [Mod Name]", "gets info on a minecraft mod");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        JsonArray versions = GeneralUtils.getJsonArray("http://bot.notenoughmods.com/?json");
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
            while(arraysize <= 20){
                versionsize = versionsize-1;
                version = versions.get(versionsize).getAsString();
                mods = GeneralUtils.getJsonArray("http://bot.notenoughmods.com/" + version + ".json");
                arraysize = mods.size();
            }
            modname = args[0].toLowerCase();
        }else{
            mods = GeneralUtils.getJsonArray("http://bot.notenoughmods.com/" + version + ".json");
        }
        int total = 0;
        for (int i = 0; i < mods.size(); i++) {
            JsonObject mod = mods.get(i).getAsJsonObject();
            if (mod.get("name").getAsString().toLowerCase().contains(modname)) {
                String Version = mod.get("version").getAsString();
                String Name = mod.get("name").getAsString();
                String Link = mod.get("shorturl").getAsString();
                if(Link.isEmpty()){
                    Link = mod.get("longurl").getAsString();
                }
                if (total < 3) {
                    IRCUtils.sendMessage(user, channel, "[" + Version + "] " + Name + " - " + Link, isPrivate);
                }
                total++;
            }
        }
        if (total == 0) {
            IRCUtils.sendError(user, "No Mods Found");
        }
    }
}


