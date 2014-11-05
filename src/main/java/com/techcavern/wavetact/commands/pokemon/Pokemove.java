package com.techcavern.wavetact.commands.pokemon;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@CMD
@GenCMD
public class Pokemove extends GenericCommand {

    public Pokemove() {
        super(GeneralUtils.toArray("pokemove pkmo"), 0, "pokemove [ID]", "Displays info on a move");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        JsonObject pokemon = GeneralUtils.getJsonObject("http://pokeapi.co/api/v1/move/" + args[0]);
        String name = pokemon.get("name").getAsString();
        String id = pokemon.get("id").getAsString();
        String description = pokemon.get("description").getAsString();
        String accuracy = pokemon.get("accuracy").getAsString();
        String power = pokemon.get("power").getAsString();
        String pp = pokemon.get("pp").getAsString();
        IRCUtils.sendMessage(user,channel,name + "(" + id + "): " + description ,isPrivate);
        IRCUtils.sendMessage(user, channel, "Accuracy: " + accuracy  +category + " - Power: " + power +" - PP: " + pp, isPrivate);
    }
}
