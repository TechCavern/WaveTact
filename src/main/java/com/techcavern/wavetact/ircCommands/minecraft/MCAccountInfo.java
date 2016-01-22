package com.techcavern.wavetact.ircCommands.minecraft;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@IRCCMD
public class MCAccountInfo extends IRCCommand {

    public MCAccountInfo() {
        super(GeneralUtils.toArray("mcaccountinfo mca mcuserinfo mcpremium mcuuid mcmigrated"), 1, "mcaccountinfo [user]", "Gets info on a minecraft account", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        String result = Request.Post("https://api.mojang.com/profiles/minecraft")
                .bodyString("[\"" + args[0] + "\",\"egewewhewhwe\"]", ContentType.APPLICATION_JSON)
                .execute()
                .returnContent().toString();
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
            IRCUtils.sendMessage(user, network, channel, "[" + IRCUtils.noPing(User) + "] " + "UUID: " + UUID + " - " + "Paid: " + Premium + " - " + "Mojang Account: " + Migrated, prefix);
        } else {
            IRCUtils.sendError(user, network, channel, "User does not exist", prefix);
        }
    }

}
