/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.eventListeners;

import com.google.gson.JsonArray;
import com.techcavern.wavetact.utils.*;
import org.apache.commons.lang3.text.WordUtils;
import org.jooq.Record;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.techcavern.wavetactdb.Tables.CHANNELPROPERTY;

/**
 * @author jztech101
 */
public class MCStatusListener implements Runnable {
    public static Map<String, String> MCStatus = new HashMap<>();

    public void run() {
        while (true){
            try {
                JsonArray mcstatus = GeneralUtils.getJsonArray("https://status.mojang.com/check");
                mcstatus.forEach(status -> {
                    String name = status.getAsJsonObject().entrySet().iterator().next().getKey().toString();
                    if (name.equalsIgnoreCase("minecraft.net")) {
                        name = "Website";
                    } else if (name.equalsIgnoreCase("api.mojang.com")) {
                        name = "API";
                    } else if (name.equalsIgnoreCase("authserver.mojang.com")) {
                        name = "AuthServer";
                    } else if (name.equalsIgnoreCase("sessionserver.mojang.com")) {
                        name = "SessionServer";
                    } else {
                        name = WordUtils.capitalize(name.replace(".minecraft.net", "").replace(".mojang.com", ""));
                    }
                    String value = status.getAsJsonObject().entrySet().iterator().next().getValue().getAsString();
                    if (value.equalsIgnoreCase("green")) {
                        value = "Online";
                    } else if (value.equalsIgnoreCase("yellow")) {
                        value = "Overloaded";
                    } else {
                        value = "Offline";
                    }
                    final String mcname = name;
                    final String mcvalue = value;
                    if(MCStatus.get(name) != null && !MCStatus.get(name).equalsIgnoreCase(value)){
                        Registry.networks.values().forEach(bot ->{
                            bot.getUserChannelDao().getAllChannels().forEach(chan ->{
                                Record prop = DatabaseUtils.getChannelProperty(IRCUtils.getNetworkNameByNetwork(bot), chan.getName(), "notifymc");
                                if(prop != null && prop.getValue(CHANNELPROPERTY.VALUE).equalsIgnoreCase("true")){
                                    IRCUtils.sendMessage(bot,chan, "[MC Status] "  + mcname + " is now " + mcvalue, "");
                                }
                            });
                        });
                    }
                    MCStatus.put(name, value);
                });
            } catch (Exception e) {

            }
            try{
                TimeUnit.MINUTES.sleep(1);
            }catch (Exception e){

            }
        }
    }
}







