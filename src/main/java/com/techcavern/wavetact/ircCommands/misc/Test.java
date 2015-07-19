package com.techcavern.wavetact.ircCommands.misc;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

//@IRCCMD
public class Test extends IRCCommand {

    public Test() {
        super(GeneralUtils.toArray("test dev"), 0, "test", "Test Command (This should not show up in a production environment. If it does, report it", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        JsonArray versions = GeneralUtils.getJsonArray("http://bot.notenoughmods.com/?json&count");
        MCModVersionSearch:
        for (int i = versions.size() - 1; i >= 0; i--) {
            int size = versions.get(i).getAsJsonObject().get("count").getAsInt();
            if (size > 10) {
                String version = versions.get(i).getAsJsonObject().get("name").getAsString();
                JsonArray mods = GeneralUtils.getJsonArray("http://bot.notenoughmods.com/" + version + ".json");
                JsonArray mods2 = GeneralUtils.getJsonArray("http://modlist.mcf.li/api/v3/" + version + ".json");
                mods2.forEach(mod2 -> {
                    JsonObject mod = mod2.getAsJsonObject();
                    String searchPhrase = mod.get("name").getAsString().replaceAll(" ", "").replaceAll("[^a-zA-Z0-9]", "");
                    boolean isTrue = false;
                    int j = 0;
                    while (j < mods.size() && !isTrue) {
                        JsonObject mod24 = mods.get(j).getAsJsonObject();
                        if (mod24.get("name").getAsString().replaceAll(" ", "").replaceAll("[^a-zA-Z0-9]", "").equalsIgnoreCase(searchPhrase)) {
                            isTrue = true;
                        }
                        j++;
                    }
                    if (!isTrue)
                        IRCUtils.sendMessage(user, network, channel, "[" + version + "] " + searchPhrase, prefix);
                });
            }

        }
    }
}
