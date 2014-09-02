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
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@CMD
@FunCMD
public class UrbanDictonary extends GenericCommand {

    public UrbanDictonary() {
        super(GeneralUtils.toArray("urbandictionary ub urban urb ud"), 0, "urbandictionary (def #) [what to define]","defines a term in the urban dictionary");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        int ArrayIndex = 0;
        if(GeneralUtils.isInteger(args[0])){
            ArrayIndex = Integer.parseInt(args[0])-1;
            args = ArrayUtils.remove(args, 0);
        }
        JsonObject objectJSON = GeneralUtils.getJsonObject("http://api.urbandictionary.com/v0/define?term=" + StringUtils.join(args).replaceAll(" ", "%20"));
        if (objectJSON.getAsJsonArray("list").size() > 0) {
           if(objectJSON.getAsJsonArray("list").size() - 1 >= ArrayIndex){
               String Word = WordUtils.capitalizeFully(objectJSON.getAsJsonArray("list").get(ArrayIndex).getAsJsonObject().get("word").getAsString().replaceAll("\\n|\\r|\\t", " ").replaceAll("  ", " "));
               String Definition = objectJSON.getAsJsonArray("list").get(ArrayIndex).getAsJsonObject().get("definition").getAsString().replaceAll("\\n|\\r|\\t", " ").replaceAll("  ", " ");
               String Examples = objectJSON.getAsJsonArray("list").get(ArrayIndex).getAsJsonObject().get("example").getAsString().replaceAll("\\n|\\r|\\t", " ").replaceAll("  ", " ");
               IRCUtils.SendMessage(user, channel, Word +": "+ Definition , isPrivate);
               if(!Examples.isEmpty()) {
                   IRCUtils.SendMessage(user, channel, "Example: " + Examples, isPrivate);
               }
            }else{
               ArrayIndex = ArrayIndex + 1;
                IRCUtils.sendError(user, "Def #" + ArrayIndex + " does not exist");
            }
            } else {
            IRCUtils.sendError(user, "Not Defined in the Urban Dictionary");
        }

    }
}
