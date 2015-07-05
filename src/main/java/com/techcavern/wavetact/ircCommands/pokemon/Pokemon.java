package com.techcavern.wavetact.ircCommands.pokemon;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.ArrayList;
import java.util.List;

@IRCCMD
public class Pokemon extends IRCCommand {

    public Pokemon() {
        super(GeneralUtils.toArray("pokemon pkm"), 0, "pokemon [name][id]", "Displays info on a pokemon", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        JsonObject pokemon = GeneralUtils.getJsonObject("http://pokeapi.co/api/v1/pokemon/" + args[0]);
        String name = pokemon.get("name").getAsString();
        String id = pokemon.get("national_id").getAsString();
        List<String> response = new ArrayList<>();
        response.add(name + "(" + id + ")");
        String description = pokemon.get("species").getAsString();
        if (!description.isEmpty())
            response.add(description);
        String height = pokemon.get("height").getAsString();
        if (!height.isEmpty())
            response.add((Double.parseDouble(height) / 10) + "m");
        String weight = pokemon.get("weight").getAsString();
        if (!weight.isEmpty())
            response.add((Double.parseDouble(weight) / 10) + "kg");
        JsonArray pretypes = pokemon.getAsJsonArray("types");
        response.add(GeneralUtils.getJsonString(pretypes, "name"));
        JsonArray preabilities = pokemon.getAsJsonArray("abilities");
        String abilities = GeneralUtils.getJsonString(preabilities, "name");
        if (abilities.isEmpty())
            abilities = "No Abilities";
        response.add(abilities);
        IRCUtils.sendMessage(user, network, channel, StringUtils.join(response, " - "), prefix);

    }
}
