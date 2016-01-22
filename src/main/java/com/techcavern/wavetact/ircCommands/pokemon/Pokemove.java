package com.techcavern.wavetact.ircCommands.pokemon;

import com.google.gson.JsonObject;
import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@IRCCMD
public class Pokemove extends IRCCommand {

    public Pokemove() {
        super(GeneralUtils.toArray("pokemove pkmo"), 1, "pokemove [id]", "Displays info on a move", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        JsonObject pokemon = GeneralUtils.getJsonObject("http://pokeapi.co/api/v1/move/" + args[0]);
        String name = pokemon.get("name").getAsString();
        String id = pokemon.get("id").getAsString();
        String description = pokemon.get("description").getAsString().replace("\n", " ");
        String accuracy = pokemon.get("accuracy").getAsString();
        String power = pokemon.get("power").getAsString();
        String pp = pokemon.get("pp").getAsString();
        IRCUtils.sendMessage(user, network, channel, "[" + name + "(" + id + ")] " + description + " - "  +"Accuracy: " + accuracy + " - Power: " + power + " - PP: " + pp, prefix);
    }
}
