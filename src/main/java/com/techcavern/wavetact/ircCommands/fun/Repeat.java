package com.techcavern.wavetact.ircCommands.fun;

import com.google.gson.JsonObject;
import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.ErrorUtils;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@IRCCMD
public class Repeat extends IRCCommand {

    public Repeat() {
        super(GeneralUtils.toArray("repeat rep"),0 , "repeat [#] (+)[message]", "repeat something a specified number of times", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        int times = Integer.parseInt(args[0]);
        args = ArrayUtils.remove(args, 0);
        if(times <= userPermLevel){
            boolean isAction = false;
            if(args[0].startsWith("+")){
                isAction = true;
                args[0] = args[0].replaceFirst("\\+", "");
            }
            if(isAction)
                for(int i=0; i<times; i++)
                    IRCUtils.sendMessage(network,channel,GeneralUtils.buildMessage(0, args.length, args),prefix);
            else
                for(int i=0; i<times; i++)
                    IRCUtils.sendAction(network,channel,GeneralUtils.buildMessage(0, args.length, args),prefix);
        }else{
            ErrorUtils.sendError(user, "Permission Denied");
        }
    }
}
