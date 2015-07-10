package com.techcavern.wavetact.ircCommands.reference;

import com.google.gson.JsonObject;
import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import static com.techcavern.wavetactdb.Tables.CONFIG;

@IRCCMD
public class Translate extends IRCCommand {

    public Translate() {
        super(GeneralUtils.toArray("translate trans t yandex"), 0, "translate [Text]", "Translates text to English", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        String yandexapikey;
        if (DatabaseUtils.getConfig("yandexapikey") != null)
            yandexapikey = DatabaseUtils.getConfig("yandexapikey").getValue(CONFIG.VALUE);
        else {
            ErrorUtils.sendError(user, "Yandex api key is null - contact bot controller to fix");
            return;
        }
        JsonObject result = GeneralUtils.getJsonObject("https://translate.yandex.net/api/v1.5/tr.json/translate?key=" + yandexapikey + "&lang=en&text=" + StringUtils.join(args, "+"));
            if(result.get("code").getAsInt() == 200){
                IRCUtils.sendMessage(user, network, channel, "[" + result.get("lang").getAsString() + "] " + result.get("text").getAsString(), prefix);
            }else{
                ErrorUtils.sendError(user,result.get("code").getAsString() + " - Unable to translate text");
            }
        }
}
