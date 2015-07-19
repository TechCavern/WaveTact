package com.techcavern.wavetact.ircCommands.misc;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@IRCCMD
public class Test extends IRCCommand {

    public Test() {
        super(GeneralUtils.toArray("test dev"), 0, "test", "Test Command (This should not show up in a production environment. If it does, report it", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        /**
         for (int i = versions.size() - 1; i >= 0; i--) {
         int size = versions.get(i).getAsJsonObject().get("count").getAsInt();
         if (size > 10) {
         String version = versions.get(i).getAsJsonObject().get("name").getAsString();
         JsonArray mods = GeneralUtils.getJsonArray("http://bot.notenoughmods.com/" + version + ".json");
         MCModSearch:
         for (int j = 0; j < mods.size(); j++) {
         JsonObject mod = mods.get(j).getAsJsonObject();
         if (mcmods.size() >= 3) {
         break MCModVersionSearch;
         }
         for (JsonObject o : mcmods.keySet()) {
         if (mod.get("name").getAsString().equalsIgnoreCase((o).get("name").getAsString()))
         continue MCModSearch;
         }
         if (mod.get(searchPhrase).getAsString().toLowerCase().contains(modname)) {
         mcmods.put(mod, version);
         }
         }
         }
         }
         **/
    }
}
