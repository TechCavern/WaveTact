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
        super(GeneralUtils.toArray("mcmods mod mcmod mods"), 0, "mcmods [MC Version#] [Mod Name]");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        JsonArray versions = GeneralUtils.getJsonArray("http://bot.notenoughmods.com/?json");
        String version = "";
        for(int i = 0; i<versions.size(); i++){
            if(args[0].equalsIgnoreCase(versions.get(i).getAsString()))
                version = args[0];
        }
        if(version == ""){
            user.send().notice("Version should be inputted as argument 1. Versions below 1.4.6 is not supported.");
        }
        IRCUtils.SendMessage(user, channel, version, isPrivate);
    }
}

