package com.techcavern.wavetact.commands.controller;

import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.GetUtils;
import com.techcavern.wavetact.utils.IRCUtils;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;


public class TestCommand extends GenericCommand {
    //   @CMD
    //   @GenCMD

    public TestCommand() {
        super(GeneralUtils.toArray("test"), 9001, null, "debug command");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate, int UserPermLevel, String... args) throws Exception {
        User c = GetUtils.getUserByNick(Bot, args[0]);
        IRCUtils.sendError(user, c.toString());
    }

}
