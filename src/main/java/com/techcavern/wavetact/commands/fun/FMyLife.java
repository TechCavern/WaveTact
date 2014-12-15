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
        super(GeneralUtils.toArray("fmylife fml"), 0, "fmylife (def #)", "Sends random fml or specified fml", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        Document doc = Jsoup.connect("http://m.fmylife.com/random").userAgent("Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.17 (KHTML, like Gecko) Chrome/24.0.1312.57 Safari/537.17").get();
        Elements FML = doc.select(".text");
        String fmylife = FML.get(0).toString().replaceAll("<.*?>", "").replaceAll("&.*?;", "");
        IRCUtils.sendMessage(user, network, channel, fmylife, prefix);
    }
}
