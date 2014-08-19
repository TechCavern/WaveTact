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
        super(GeneralUtils.toArray("mcmods mcmod"), 0, "mcmods [MC Version#] [Mod Name]"," gets info on a minecraft mod");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        JsonArray versions = GeneralUtils.getJsonArray("http://bot.notenoughmods.com/?json");
        String version = "";
        String modname = "";
        for(int i = 0; i<versions.size(); i++){
            String vers = versions.get(i).getAsString();
            if(vers.contains(args[0])) {
                version = vers;
                modname = args[1].toLowerCase();
            }
        }
        if(version == ""){
            version = versions.get(versions.size()-1).getAsString();
            modname = args[0].toLowerCase();
        }
        JsonArray mods = GeneralUtils.getJsonArray("http://bot.notenoughmods.com/" + version + ".json");
            int total = 0;
            for(int i = 0; i<mods.size(); i++) {
                JsonObject mod = mods.get(i).getAsJsonObject();
                if (mod.get("name").getAsString().toLowerCase().contains(modname)) {
                    String Version = mod.get("version").getAsString();
                    String Name = mod.get("name").getAsString();
                    String Link = mod.get("shorturl").getAsString();
                    if (total < 3) {
                        IRCUtils.SendMessage(user, channel, "[" + Version + "]" + Name + " - " + Link, isPrivate);
                    }
                    total++;
                }
            }
            if(total == 0){
                IRCUtils.sendError(user, "No Mods Found");
            }
        }
    }


