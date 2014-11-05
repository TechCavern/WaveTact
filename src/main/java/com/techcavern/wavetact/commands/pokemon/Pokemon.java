package com.techcavern.wavetact.commands.pokemon;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.apache.commons.lang3.StringUtils;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@CMD
@GenCMD
public class Pokemon extends GenericCommand {

    public Pokemon() {
        super(GeneralUtils.toArray("pokemon pkm"), 0, "pokemon [name][ID]", "Displays info on a pokemon");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        JsonObject pokemon = GeneralUtils.getJsonObject("http://pokeapi.co/api/v1/pokemon/" + args[0]);
        String name = pokemon.get("name").getAsString();
        String id = pokemon.get("national_id").getAsString();
        List<String> response = new ArrayList<>();
        response.add(name + "(" + id + ")");
        String description = pokemon.get("species").getAsString();
        if(!description.isEmpty())
        response.add(description);
        String height = pokemon.get("height").getAsString();
        if(!height.isEmpty())
        response.add((Double.parseDouble(height)/10) + "m");
        String weight = pokemon.get("weight").getAsString();
        if(!weight.isEmpty())
        response.add((Double.parseDouble(weight)/10) + "kg");
        JsonArray pretypes = pokemon.getAsJsonArray("types");
        response.add(GeneralUtils.getJsonString(pretypes, "name"));
        JsonArray preabilities = pokemon.getAsJsonArray("abilities");
        String abilities = GeneralUtils.getJsonString(preabilities, "name");
        if(abilities.isEmpty())
            abilities = "No Abilities";
        response.add(abilities);
        IRCUtils.sendMessage(user, channel, StringUtils.join(response, " - "), isPrivate);

    }
}
