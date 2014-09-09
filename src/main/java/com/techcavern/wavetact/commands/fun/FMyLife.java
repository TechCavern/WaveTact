package com.techcavern.wavetact.commands.fun;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GenCMD;
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
@GenCMD
public class FMyLife extends GenericCommand {

    public FMyLife() {
        super(GeneralUtils.toArray("fmylife fml"), 0, "fmylife (def #)", "Random FML");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        Document doc = Jsoup.connect("http://m.fmylife.com/random").get();
        Elements FML = doc.select(".text");
        String c = FML.get(0).toString().replaceAll("<.*?>", "").replaceAll("&.*?;", "");
        IRCUtils.sendMessage(user, channel, c, isPrivate);
    }
}
