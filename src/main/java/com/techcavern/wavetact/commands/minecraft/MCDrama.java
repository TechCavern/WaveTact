package com.techcavern.wavetact.commands.minecraft;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.FunCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@CMD
@FunCMD
public class MCDrama extends GenericCommand {

    public MCDrama() {
        super(GeneralUtils.toArray("mcdrama"), 0, "mcdrama", "Displays Minecraft Drama");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        Document doc = Jsoup.connect("http://asie.pl/drama.php?plain").get();
        String c = doc.body().text();
        IRCUtils.SendMessage(user, channel, c , isPrivate);
    }
}
