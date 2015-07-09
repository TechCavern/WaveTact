package com.techcavern.wavetact.ircCommands.fun;

import com.google.gson.JsonObject;
import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@IRCCMD
public class UrbanDictionary extends IRCCommand {

    public UrbanDictionary() {
        super(GeneralUtils.toArray("urbandictionary ub urban urb ud"), 0, "urbandictionary (def #) [what to define]", "Defines a term in the urban dictionary", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        int ArrayIndex = 0;
        if (GeneralUtils.isInteger(args[0])) {
            ArrayIndex = Integer.parseInt(args[0]) - 1;
            args = ArrayUtils.remove(args, 0);
        }
        JsonObject objectJSON = GeneralUtils.getJsonObject("http://api.urbandictionary.com/v0/define?term=" + StringUtils.join(args).replaceAll(" ", "%20"));
        if (objectJSON.getAsJsonArray("list").size() > 0) {
            if (objectJSON.getAsJsonArray("list").size() - 1 >= ArrayIndex) {
                String Word = WordUtils.capitalizeFully(objectJSON.getAsJsonArray("list").get(ArrayIndex).getAsJsonObject().get("word").getAsString().replaceAll("\\n|\\r|\\t", " ").replaceAll("  ", " "));
                String Definition = objectJSON.getAsJsonArray("list").get(ArrayIndex).getAsJsonObject().get("definition").getAsString().replaceAll("\\n|\\r|\\t", " ").replaceAll("  ", " ");
                String Examples = objectJSON.getAsJsonArray("list").get(ArrayIndex).getAsJsonObject().get("example").getAsString().replaceAll("\\n|\\r|\\t", " ").replaceAll("  ", " ");
                IRCUtils.sendMessage(user, network, channel, "[" + Word + "] " + Definition, prefix);
                if (!Examples.isEmpty()) {
                    IRCUtils.sendMessage(user, network, channel, "[Example] " + Examples, prefix);
                }
            } else {
                ArrayIndex = ArrayIndex + 1;
                ErrorUtils.sendError(user, "Def #" + ArrayIndex + " does not exist");
            }
        } else {
            ErrorUtils.sendError(user, "Not defined in the urban dictionary");
        }

    }
}
