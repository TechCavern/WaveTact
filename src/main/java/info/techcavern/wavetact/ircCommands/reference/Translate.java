package info.techcavern.wavetact.ircCommands.reference;

import com.google.gson.JsonObject;
import info.techcavern.wavetact.annot.IRCCMD;
import info.techcavern.wavetact.objects.IRCCommand;
import info.techcavern.wavetact.utils.DatabaseUtils;
import info.techcavern.wavetact.utils.GeneralUtils;
import info.techcavern.wavetact.utils.IRCUtils;
import info.techcavern.wavetact.annot.IRCCMD;
import info.techcavern.wavetact.objects.IRCCommand;
import info.techcavern.wavetact.utils.DatabaseUtils;
import info.techcavern.wavetact.utils.GeneralUtils;
import info.techcavern.wavetact.utils.IRCUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import static info.techcavern.wavetactdb.Tables.CONFIG;

@IRCCMD
public class Translate extends IRCCommand {

    public Translate() {
        super(GeneralUtils.toArray("translate trans tr yandex"), 1, "translate [Text]", "Translates text to English", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        String yandexapikey;
        if (DatabaseUtils.getConfig("yandexapikey") != null)
            yandexapikey = DatabaseUtils.getConfig("yandexapikey").getValue(CONFIG.VALUE);
        else {
            IRCUtils.sendError(user, network, channel, "Yandex api key is null - contact bot controller to fix", prefix);
            return;
        }
        JsonObject result = GeneralUtils.getJsonObject("https://translate.yandex.net/api/v1.5/tr.json/translate?key=" + yandexapikey + "&lang=en&text=" + StringUtils.join(args, "+"));
        if (result.get("code").getAsInt() == 200) {
            IRCUtils.sendMessage(user, network, channel, "[" + result.get("lang").getAsString() + "] " + result.get("text").getAsString(), prefix);
        } else {
            IRCUtils.sendError(user, network, channel, result.get("code").getAsString() + " - Unable to translate text", prefix);
        }
    }
}
