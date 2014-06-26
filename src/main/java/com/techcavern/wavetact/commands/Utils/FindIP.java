package com.techcavern.wavetact.commands.Utils;

import com.google.gson.JsonObject;
import com.techcavern.wavetact.objects.Command;
import com.techcavern.wavetact.utils.GeneralUtils;
import org.pircbotx.hooks.events.MessageEvent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jztech101 on 6/26/14.
 */
public class FindIP extends Command {
    public FindIP() {
        super("find", 0, "findip [ipv4]");
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {
        JsonObject g = GeneralUtils.getJsonObject("http://freegeoip.net/json/" + args[0]);
        event.getChannel().send().message(g.get("city").getAsString() + " , " + g.get("region_name").getAsString() + " , " + g.get("zipcode").getAsString());


    }
}
