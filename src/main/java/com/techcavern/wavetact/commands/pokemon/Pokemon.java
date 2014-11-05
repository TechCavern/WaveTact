package com.techcavern.wavetact.commands.pokemon;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.deploy.util.StringUtils;
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
        response.add((Integer.parseInt(height)*0.1) + "m");
        String weight = pokemon.get("weight").getAsString();
        if(!weight.isEmpty())
            response.add((Integer.parseInt(weight)*0.1) + "kg");
        String types = "";
        JsonArray pretypes = pokemon.getAsJsonArray("types");
        for(int i = 0; i < pretypes.size(); i++){
            if(i == 0 ){
                types = pretypes.get(i).getAsJsonObject().get("name").getAsString();
            }else{
                types += ", " +pretypes.get(i).getAsJsonObject().get("name").getAsString();
            }
        }
        response.add(types);
        String abilities = "";
        JsonArray preabilities = pokemon.getAsJsonArray("abilities");
        for(int i = 0; i < preabilities.size(); i++){
            if(i == 0 ){
                abilities = preabilities.get(i).getAsJsonObject().get("name").getAsString();
            }else{
                abilities += ", " +preabilities.get(i).getAsJsonObject().get("name").getAsString();
            }
        }
        if(!abilities.isEmpty())
        response.add(abilities);
        IRCUtils.sendMessage(user, channel, StringUtils.join(response, " - "), isPrivate);

    }
}
