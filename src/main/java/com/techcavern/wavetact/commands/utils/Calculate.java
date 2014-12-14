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
        super(GeneralUtils.toArray("calculate calc math"), 0, "calculate [expression]", "calculates a math expression", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        ExpressionBuilder expr = new ExpressionBuilder(args[0]);
        IRCUtils.sendMessage(user, network, channel, Double.toString(expr.build().evaluate()), prefix);
    }

}
