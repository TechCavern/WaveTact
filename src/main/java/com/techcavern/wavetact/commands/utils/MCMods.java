package com.techcavern.wavetact.commands.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.net.URLEncoder;


public class MCMods extends GenericCommand {
    @CMD
    @GenCMD

    public MCMods() {
        super(GeneralUtils.toArray("mcmods mcmod"), 0, "mcmods [MC Version#] [Mod Name] - gets info on a mod");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        JsonArray versions = GeneralUtils.getJsonArray("http://bot.notenoughmods.com/?json");
        String version = "";
        for(int i = 0; i<versions.size(); i++){
            String vers = versions.get(i).getAsString();
            if(vers.contains(args[0]))
                version = vers;
        }
        if(version == ""){
            user.send().notice("Version not found. Versions below 1.4.5 is not supported.");
        }else{
            JsonArray mods = GeneralUtils.getJsonArray("http://bot.notenoughmods.com/" + version + ".json");
            int total = 0;
            for(int i = 0; i<mods.size(); i++){
                JsonObject mod = mods.get(i).getAsJsonObject();
                if(mod.get("name").getAsString().toLowerCase().contains(args[1].toLowerCase())){
                    if(total < 3){
                        IRCUtils.SendMessage(user, channel, mod.get("name").getAsString() + " - version: " + mod.get("version").getAsString() + " - link: " + mod.get("shorturl").getAsString(), isPrivate);
                    }
                    total++;
                }
            }
            if(total == 0){
                user.send().notice("No Mods Found");
            }
        }
    }
}

