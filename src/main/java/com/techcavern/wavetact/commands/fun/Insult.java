package com.techcavern.wavetact.commands.fun;

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

@CMD
@GenCMD
public class Insult extends GenericCommand {

    public Insult() {
        super(GeneralUtils.toArray("insult burn"), 0, "insult [something]", "insults something", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        Document doc = Jsoup.connect("http://www.insultgenerator.org/").get();
        String c = doc.select("td").text();
        if (args[0].equalsIgnoreCase(network.getNick()))
            IRCUtils.sendMessage(user, network, channel, user.getNick() + ": " + c, prefix);
        else
            IRCUtils.sendMessage(user, network, channel, args[0] + ": " + c, prefix);
    }
}
