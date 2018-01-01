package com.techcavern.wavetact.ircCommands.fun;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.Registry;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@IRCCMD
public class RandomInteger extends IRCCommand {

    public RandomInteger() {
        super(GeneralUtils.toArray("randominteger randomint randint randinteger randnum randomnumber randnumber randomnum"), 0, "randominteger [value]", "generates a random integer between 0 and the number specified exclusive", false);
    }

    @Override
    public void onCommand(String command, User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        boolean isNegative=false;
        if(args[0].contains("-")){
            isNegative=true;
        }
        int x = Registry.randNum.nextInt(Integer.valueOf(args[0].replace("-","")));
        if(isNegative)
            IRCUtils.sendMessage(user, network, channel, "-"+String.valueOf(x), prefix);

        else
        IRCUtils.sendMessage(user, network, channel, String.valueOf(x), prefix);

    }
}
