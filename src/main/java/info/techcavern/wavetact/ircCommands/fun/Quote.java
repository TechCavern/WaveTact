package info.techcavern.wavetact.ircCommands.fun;

import info.techcavern.wavetact.annot.IRCCMD;
import info.techcavern.wavetact.objects.IRCCommand;
import info.techcavern.wavetact.utils.GeneralUtils;
import info.techcavern.wavetact.utils.IRCUtils;
import info.techcavern.wavetact.utils.Registry;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;


@IRCCMD
public class Quote extends IRCCommand {

    public Quote() {
        super(GeneralUtils.toArray("quote quo randquote"), 1, "quote", "Gets a random quote", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        int randomint = Registry.randNum.nextInt(Registry.quotetopics.size());
        String topic = Registry.quotetopics.toArray()[randomint].toString();
        Document doc = Jsoup.connect("https://www.goodreads.com/quotes/tag/"+ topic + "?page=" + Registry.randNum.nextInt(100)).userAgent(Registry.USER_AGENT).get();
        Elements quotes = doc.select(".quote");
        Element quote = quotes.get(Registry.randNum.nextInt(quotes.size()-1));
        String d = quote.select(".quotetext").text();
        IRCUtils.sendMessage(user, network, channel, "[" + GeneralUtils.capitalizeFirstLetter(topic) + "] " +d.replaceAll("\n"," "), prefix);
    }
}
