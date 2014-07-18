package com.techcavern.wavetact.commands.controller;


import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.utils.objects.GenericCommand;
import com.techcavern.wavetact.utils.GeneralUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;

public class IRCRaw extends GenericCommand {
    @CMD
    public IRCRaw() {
        super(GeneralUtils.toArray("ircraw raw quote"), 9001, "raw [to be sent to server]");
    }

    @Override
    public void onCommand(User user, PircBotX Bot, Channel channel, boolean isPrivate,int UserPermLevel, String... args) throws Exception {
        Bot.sendRaw().rawLine(GeneralUtils.buildMessage(0, args.length, args));
    }
}

