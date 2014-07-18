package com.techcavern.wavetact.commands.utils;

import com.google.gson.JsonObject;
import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;


public class FindIP extends GenericCommand {
    @CMD
    public FindIP() {
        super(GeneralUtils.toArray("findip locate find loc"), 0, "findip [ipv4][user]");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate,int UserPermLevel, String... args) throws Exception {
        JsonObject objectJson;
        if(args[0].contains(".")) {
            objectJson = GeneralUtils.getJsonObject("http://freegeoip.net/json/" + args[0]);
        }else{
            objectJson = GeneralUtils.getJsonObject("http://freegeoip.net/json/" + GetUtils.getUserByNick(Bot,args[0]).getHostmask());
        }
        String location = objectJson.get("city").getAsString() + ", " + objectJson.get("region_name").getAsString() + ", " + objectJson.get("zipcode").getAsString();
        if (location.equalsIgnoreCase(", , ")) {
            user.send().notice("IP is Protected");
        } else
        {
            IRCUtils.SendMessage(user, channel, location,isPrivate);
        }

    }
}
