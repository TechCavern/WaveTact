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
public class Pokemove extends IRCCommand {

    public Pokemove() {
        super(GeneralUtils.toArray("pokemove pokemonmove pkmo"), 1, "pokemove [ability]", "Displays info on a move", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        try {

            Document pokemon = Jsoup.connect("http://bulbapedia.bulbagarden.net/wiki/" + StringUtils.capitalize(StringUtils.join(args," ")).replaceAll(" ", "%20") + "_(move)").userAgent(Registry.USER_AGENT).get();
            List<String> preResponse = new ArrayList<>();
            pokemon.getElementsByTag("tbody").get(0).getElementsByTag("span").iterator().forEachRemaining(i -> {
                String eg = GeneralUtils.stripHTML(i.toString());
                preResponse.add(eg);
            });
            List<String> response = new ArrayList<>();
            response.add(preResponse.get(1) +": " + preResponse.get(2));
            response.add(preResponse.get(3) + ": "+ preResponse.get(4));
            IRCUtils.sendMessage(user, network, channel, "[" + GeneralUtils.stripHTML(pokemon.select("b").get(1).toString()) + "] " + StringUtils.join(response, " - "), prefix);
        } catch (HttpStatusException e) {
            if (e.getStatusCode() == 404) {
                IRCUtils.sendError(user, network, channel, "Pokemon Move not found!", prefix);
            }
        }
    }
}
