package com.techcavern.wavetact.ircCommands.pokemon;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.Registry;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@IRCCMD
public class Pokemon extends IRCCommand {

    public Pokemon() {
        super(GeneralUtils.toArray("pokemon pkm"), 1, "pokemon [pokemon]", "Displays info on a pokemon", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        try {
            Document pokemon = Jsoup.connect("http://bulbapedia.bulbagarden.net/wiki/" + StringUtils.join(args, "%20") + "_(Pokemon)").userAgent(Registry.USER_AGENT).get();
            List<String> preResponse = new ArrayList<>();
            pokemon.select(".roundy").get(1).getElementsByTag("span").iterator().forEachRemaining(i -> {
                String eg = GeneralUtils.stripHTML(i.toString());
                preResponse.add(eg);
            });
            List<String> response = new ArrayList<>();
            response.add(preResponse.get(0));
            response.add(preResponse.get(3));
            response.add(preResponse.get(4) + ": " + GeneralUtils.buildMessage(5,16, preResponse.toArray(new String[preResponse.size()])).replaceAll("Unknown","").trim().replaceAll(" ", ", "));
            response.add(preResponse.get(17) + ": " + StringUtils.stripEnd(GeneralUtils.buildMessage(18,24, preResponse.toArray(new String[preResponse.size()])).replaceAll("Unknown","").replaceAll("  ","").replaceAll(" ", ", "),", "));
            IRCUtils.sendMessage(user, network, channel, "[" + GeneralUtils.stripHTML(pokemon.select("b").get(1).toString()) + "] " + StringUtils.join(response, " - "), prefix);
        }catch(HttpStatusException e){
            if(e.getStatusCode() ==  404){
                IRCUtils.sendError(user,network,channel, "Pokemon not found!", prefix);
            }
        }
    }
}
