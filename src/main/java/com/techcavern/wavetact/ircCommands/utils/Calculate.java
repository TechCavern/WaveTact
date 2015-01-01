package com.techcavern.wavetact.ircCommands.utils;

import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@IRCCMD
public class Calculate extends IRCCommand {

    public Calculate() {
        super(GeneralUtils.toArray("calculate calc math"), 0, "calculate [expression]", "calculates a math expression", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        ExpressionBuilder expr = new ExpressionBuilder(StringUtils.join(args, ""));
        IRCUtils.sendMessage(user, network, channel, Double.toString(expr.build().evaluate()), prefix);
    }

}
