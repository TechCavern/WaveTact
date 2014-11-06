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
        super(GeneralUtils.toArray("insult burn"), 0, "insult [something]", "insults something");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        Document doc = Jsoup.connect("http://www.insultgenerator.org/").get();
        String c = doc.select("td").text();
        if(args[0].equalsIgnoreCase(Bot.getNick()))
            IRCUtils.sendMessage(user, channel,user.getNick() + ": " +  c, isPrivate);
        else
        IRCUtils.sendMessage(user, channel,args[0] + ": " +  c, isPrivate);

    }
}
