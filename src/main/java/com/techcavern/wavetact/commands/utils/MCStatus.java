package com.techcavern.wavetact.commands.utils;

import com.google.gson.JsonObject;
import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.apache.commons.lang3.text.WordUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;


public class MCStatus extends GenericCommand {
    @CMD
    public MCStatus() {
        super(GeneralUtils.toArray("mcstatus mc"), 0, "mcstatus - takes zero arguments, checks status of MC servers");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, String... args) throws Exception {
        String Result;
        String Res;
        URL url;
        url = new URL("https://status.mojang.com/check");
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        Result = reader.readLine().replace("\"},{\""," | " ).replace("\":\"", ": ").replace("green", "Online").replace("red", "Offline").replace("[{\"", "").replace("\"}]", "").replace(".minecraft.net", "").replace(".mojang.com", "").replace("server","");
        Result = WordUtils.capitalizeFully(Result);
        if(Result != null) {
            IRCUtils.SendMessage(user, channel, Result, isPrivate);
        }else{
            user.send().notice("MC Status Currently Unavailable");
        }
        }

    }

