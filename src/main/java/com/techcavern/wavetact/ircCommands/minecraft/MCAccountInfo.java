package com.techcavern.wavetact.ircCommands.minecraft;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

@IRCCMD
public class MCAccountInfo extends IRCCommand {

    public MCAccountInfo() {
        super(GeneralUtils.toArray("mcaccountinfo mca mcuserinfo mcpremium mcuuid mcmigrated"), 0, "mcaccountinfo [user]", "Gets info on a minecraft account", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        URL url = new URL("https://api.mojang.com/profiles/minecraft");

        String payload = "[\"" + args[0] + "\",\"egewewhewhwe\"]";
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
        writer.write(payload);
        writer.close();
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String result = "";
        String line;
        while ((line = br.readLine()) != null) {
            result += line.replaceAll("\n", " ") + "\n";
        }
        br.close();
        connection.disconnect();
        JsonArray mcapipre = new JsonParser().parse(result).getAsJsonArray();
        if (mcapipre.size() > 0) {
            JsonObject mcapi = mcapipre.get(0).getAsJsonObject();
            String User = mcapi.get("name").getAsString();
            String UUID = mcapi.get("id").getAsString();
            String Premium = "True";
            if (mcapi.get("demo") != null && mcapi.get("legacy").getAsString().equalsIgnoreCase("True")) {
                Premium = "False";
            }
            String Migrated = "True";
            if (mcapi.get("legacy") != null && mcapi.get("legacy").getAsString().equalsIgnoreCase("True")) {
                Migrated = "False";
            }
            IRCUtils.sendMessage(user, network, channel, "[" + User + "] " + "UUID: " + UUID + " - " + "Paid: " + Premium + " - " + "Mojang Account: " + Migrated, prefix);
        } else {
            ErrorUtils.sendError(user, "User does not exist");
        }
    }

}
