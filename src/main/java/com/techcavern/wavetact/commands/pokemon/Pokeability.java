package com.techcavern.wavetact.commands.pokemon;

import com.google.gson.JsonObject;
import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

//@CMD
//@GenCMD
//Deregistered due to lack of usefulness with missing descriptions and all
public class Pokeability extends GenericCommand {

    public Pokeability() {
        super(GeneralUtils.toArray("pokeability pka"), 0, "pokeability [ID]", "Displays info on an ability");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        JsonObject pokemon = GeneralUtils.getJsonObject("http://pokeapi.co/api/v1/ability/" + args[0]);
        String name = pokemon.get("name").getAsString();
        String id = pokemon.get("id").getAsString();
        String description = pokemon.get("description").getAsString().replace("\n", " ");
        IRCUtils.sendMessage(user,channel,name + "(" + id + "): " + description ,isPrivate);
    }
}
