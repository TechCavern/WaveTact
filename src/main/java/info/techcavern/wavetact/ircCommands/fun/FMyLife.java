package info.techcavern.wavetact.ircCommands.fun;

import info.techcavern.wavetact.annot.IRCCMD;
import info.techcavern.wavetact.objects.IRCCommand;
import info.techcavern.wavetact.utils.GeneralUtils;
import info.techcavern.wavetact.utils.IRCUtils;
import info.techcavern.wavetact.utils.Registry;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@IRCCMD
public class FMyLife extends IRCCommand {

    public FMyLife() {
        super(GeneralUtils.toArray("fmylife fml"), 1, "fmylife", "Sends random fml", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        Document doc = Jsoup.connect("http://fmylife.com/random").userAgent(Registry.USER_AGENT).get();
        Elements FML = doc.select(".art-panel");
        String fmylife = GeneralUtils.stripHTML(FML.get(0).select(".panel-content").select(".block").select("a").toString());
        IRCUtils.sendMessage(user, network, channel, fmylife, prefix);
    }
}
