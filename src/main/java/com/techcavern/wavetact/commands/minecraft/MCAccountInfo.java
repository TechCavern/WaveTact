package com.techcavern.wavetact.commands.minecraft;

import com.google.gson.JsonObject;
import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@CMD
@GenCMD
public class MCAccountInfo extends GenericCommand {

    public MCAccountInfo() {
        super(GeneralUtils.toArray("mcaccountinfo mcuserinfo mcpremium mcuuid mcmigrated"), 0, "mcaccountinfo [user]", "Gets Info on a Minecraft Account");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        if (GeneralRegistry.minecraftapikey == null) {
            IRCUtils.sendError(user, "Minecraft API key is null - Contact Bot Controller to fix");
            return;
        }
        JsonObject mcapi = GeneralUtils.getJsonObject("http://theminecraftapi.com/v1/?user=" + args[0] + "&apiKey=" + GeneralRegistry.minecraftapikey);
        if (mcapi.get("user") != null) {
            String User = mcapi.get("user").getAsString();
            String UUID = mcapi.get("uuid").getAsString();
            String Premium = mcapi.get("premium").getAsString();
            String Migrated = mcapi.get("migrated").getAsString();
            IRCUtils.sendMessage(user, channel, User + ": " + "UUID: " + UUID + " - " + "Paid: " + Premium + " - " + "Mojang Account: " + Migrated, isPrivate);
        } else {
            IRCUtils.sendError(user, "user does not exist");
        }
    }

}
