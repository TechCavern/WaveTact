package com.techcavern.wavetact.commands.pokemon;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sun.deploy.util.StringUtils;
import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.ArrayList;
import java.util.List;

@CMD
@GenCMD
public class Poketypes extends GenericCommand {

    public Poketypes() {
        super(GeneralUtils.toArray("poketype pkt"), 0, "poketype [ID]", "Displays info on a type");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        JsonObject pokemon = GeneralUtils.getJsonObject("http://pokeapi.co/api/v1/type/" + args[0]);
        String name = pokemon.get("name").getAsString();
        String id = pokemon.get("id").getAsString();
        IRCUtils.sendMessage(user,channel,name + "(" + id + "):",isPrivate);
        JsonArray preineffective = pokemon.getAsJsonArray("ineffective");
        String ineffective = GeneralUtils.getJsonString(preineffective, "name");
        if(ineffective.isEmpty())
            ineffective = "No ineffective types";
        IRCUtils.sendMessage(user,channel,"Ineffective to: " + ineffective,isPrivate);
        JsonArray presupereffective = pokemon.getAsJsonArray("super_effective");
        String supereffective = GeneralUtils.getJsonString(presupereffective, "name");
        if(supereffective.isEmpty())
            supereffective = "No super effective types";
        IRCUtils.sendMessage(user,channel,"Super Effective to: " + supereffective,isPrivate);
    }
}
