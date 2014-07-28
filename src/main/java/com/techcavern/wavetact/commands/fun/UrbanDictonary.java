package com.techcavern.wavetact.commands.fun;

import com.google.gson.JsonObject;
import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.FunCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.apache.commons.lang3.StringUtils;
import org.pircbotz.Channel;
import org.pircbotz.PircBotZ;
import org.pircbotz.User;

public class UrbanDictonary extends GenericCommand {
    @CMD
    @FunCMD

    public UrbanDictonary() {
        super(GeneralUtils.toArray("urbandictionary ub urban urb ud"), 0, "urbandictionary [what to define]");
    }

    @Override
    public void onCommand(User user, PircBotZ Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        JsonObject objectJSON = GeneralUtils.getJsonObject("http://hooks.urbandictionary.com/v0/define?term=" + StringUtils.join(args).replaceAll(" ", "%20"));
        if (objectJSON.getAsJsonArray("list").size() > 0) {
            IRCUtils.SendMessage(user, channel, objectJSON.getAsJsonArray("list").get(0).getAsJsonObject().get("definition").getAsString().replaceAll("\\n|\\r|\\t", " ").replaceAll("  ", " "), isPrivate);
        } else {
            user.send().notice("Not Defined in the Urban Dictionary");
        }

    }
}
