package com.techcavern.wavetact.commands.fun;

import com.google.gson.JsonObject;
import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.FunCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@CMD
@FunCMD
public class FMyLife extends GenericCommand {

    public FMyLife() {
        super(GeneralUtils.toArray("fmylife fml"), 0, "fmylife (def #)","Random FML");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        Document doc = Jsoup.connect("http://m.fmylife.com/random").get();
        Elements FML = doc.select(".text");
        String c = FML.get(0).toString().replaceAll("\\<.*?>","").replaceAll("\\&.*?;", "");
        IRCUtils.SendMessage(user, channel, c , isPrivate);
    }
}
