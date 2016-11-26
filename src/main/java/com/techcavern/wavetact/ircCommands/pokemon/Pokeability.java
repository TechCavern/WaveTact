package com.techcavern.wavetact.ircCommands.pokemon;

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
import java.util.List;

@IRCCMD
public class Pokeability extends IRCCommand {

    public Pokeability() {
        super(GeneralUtils.toArray("pokeability pokemonability pka"), 1, "pokeability [ability]", "Displays info on a pokemon ability", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        try {
            Document pokemon = Jsoup.connect("http://bulbapedia.bulbagarden.net/wiki/" + StringUtils.capitalize(StringUtils.join(args, " ")).replaceAll(" ", "%20") + "_(Ability)").userAgent(Registry.USER_AGENT).get();
            IRCUtils.sendMessage(user, network, channel, "[" + GeneralUtils.stripHTML(pokemon.select("b").get(1).toString()) + "] " + GeneralUtils.stripHTML(pokemon.getElementsByTag("tbody").get(0).getElementsByTag("td").get(14).toString()), prefix);
        } catch (HttpStatusException e) {
            if (e.getStatusCode() == 404) {
                IRCUtils.sendError(user, network, channel, "Pokemon Ability not found!", prefix);
            }
        }
    }
}
