package com.techcavern.wavetact.ircCommands.pokemon;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@IRCCMD
public class Poketype extends IRCCommand {

    public Poketype() {
        super(GeneralUtils.toArray("poketype pkt"), 1, "poketype [id]", "Displays info on a type", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        JsonObject pokemon = GeneralUtils.getJsonObject("http://pokeapi.co/api/v1/type/" + args[0]);
        String name = pokemon.get("name").getAsString();
        String id = pokemon.get("id").getAsString();
        IRCUtils.sendMessage(user, network, channel, "[" + name + "(" + id + ")] ", prefix);
        JsonArray preineffective = pokemon.getAsJsonArray("ineffective");
        String ineffective = GeneralUtils.getJsonString(preineffective, "name");
        if (ineffective.isEmpty())
            ineffective = "No ineffective types";
        IRCUtils.sendMessage(user, network, channel, "Ineffective to: " + ineffective, prefix);
        JsonArray presupereffective = pokemon.getAsJsonArray("super_effective");
        String supereffective = GeneralUtils.getJsonString(presupereffective, "name");
        if (supereffective.isEmpty())
            supereffective = "No super effective types";
        IRCUtils.sendMessage(user, network, channel, "Super effective to: " + supereffective, prefix);
    }
}
