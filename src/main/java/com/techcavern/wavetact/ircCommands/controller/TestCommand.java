package com.techcavern.wavetact.ircCommands.controller;

import com.techcavern.wavetact.annot.ConCMD;
import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.IRCCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

@IRCCMD
@ConCMD
public class TestCommand extends IRCCommand {

    public TestCommand() {
        super(GeneralUtils.toArray("test"), 9001, null, "Debug command", false);
    }

    @Override
    public void onCommand(User user, PircBotX network, String prefix, Channel channel, boolean isPrivate, int userPermLevel, String... args) throws Exception {
        user.send().notice("hi");
    }

}
