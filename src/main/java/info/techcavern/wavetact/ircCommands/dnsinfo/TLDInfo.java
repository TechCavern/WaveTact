package info.techcavern.wavetact.ircCommands.dnsinfo;

import info.techcavern.wavetact.annot.IRCCMD;
import info.techcavern.wavetact.objects.IRCCommand;
import info.techcavern.wavetact.utils.GeneralUtils;
import info.techcavern.wavetact.utils.IRCUtils;
import info.techcavern.wavetact.utils.Registry;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.HashSet;
import java.util.Set;

@IRCCMD
public class TLDInfo extends IRCCommand {

    public TLDInfo() {
        super(GeneralUtils.toArray("tldinfo tld iana"), 1, "tld [tld]", "Gets information on a TLDInfo from the iana db", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        if (!args[0].startsWith(".")) {
            args[0] = "." + args[0];
        }
        try {
            Document doc = Jsoup.connect("http://www.iana.org/domains/root/db/" + args[0]).userAgent(Registry.USER_AGENT).get();
            Elements titles = doc.select("#main_right").select("h2");
            Elements names = doc.select("#main_right").select("b");
            String[] organization = doc.select("#main_right").after("br").html().split("\n");
            Set<String> results = new HashSet<>();
            results.add("Sponsoring Organization: "+ names.get(0).text());
            results.add("Administrative Contact: " + names.get(1).text()  + ", " +  GeneralUtils.stripHTML(organization[10]));
            results.add("Technical Contact: " + names.get(5).text() + ", " + GeneralUtils.stripHTML(organization[23]));
            IRCUtils.sendMessage(user, network, channel, StringUtils.join(results, " - "), prefix);
        }catch(HttpStatusException e){
            if(e.getStatusCode() == 404){
                IRCUtils.sendError(user, network, channel, "Invalid TLD", prefix);
            }else{
                IRCUtils.sendError(user, network, channel, "Failed to execute command, please make sure you are using the correct syntax (" + getSyntax() + ")", prefix);

            }
        }

    }
}
