package info.techcavern.wavetact.ircCommands.fun;

import info.techcavern.wavetact.annot.IRCCMD;
import info.techcavern.wavetact.objects.IRCCommand;
import info.techcavern.wavetact.utils.GeneralUtils;
import info.techcavern.wavetact.utils.IRCUtils;
import info.techcavern.wavetact.utils.Registry;
import org.apache.commons.lang3.RandomUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@IRCCMD
public class Button extends IRCCommand {

    public Button() {
        super(GeneralUtils.toArray("willyoupushthisbutton button"), 1, "willyoupushthisbutton", "Will you push this button?", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        Document doc = Jsoup.connect("http://willyoupressthebutton.com").userAgent(Registry.USER_AGENT).get();
        String button = "The button reads \"" +GeneralUtils.stripHTML(doc.select("#cond").toString().replaceAll("&.*?;", "")).trim() + " BUT " + GeneralUtils.stripHTML(doc.select("#res").toString().replaceAll("&.*?;", "")).trim() + "\", will you push the button?";
        IRCUtils.sendMessage(user, network, channel, button, prefix);
    }
}