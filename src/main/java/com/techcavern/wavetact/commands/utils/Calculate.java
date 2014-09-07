package com.techcavern.wavetact.commands.utils;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.annot.GenCMD;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@CMD
@GenCMD
public class Calculate extends GenericCommand {

    public Calculate() {
        super(GeneralUtils.toArray("calculate calc math"), 0, "calculate [expression]", "calculates a math expression");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        ExpressionBuilder expr = new ExpressionBuilder(args[0]);
        IRCUtils.SendMessage(user, channel, Double.toString(expr.build().evaluate()), isPrivate);
    }

}
