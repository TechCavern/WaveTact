package com.techcavern.wavetact.commands.controller;


import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.objects.Command;
import com.techcavern.wavetact.utils.GeneralUtils;
import org.pircbotx.hooks.events.MessageEvent;

public class IRCRaw extends Command {
    @CMD
    public IRCRaw() {
        super(GeneralUtils.toArray("raw ircraw quote"), 9001, "raw [to be sent to server]");
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args) throws Exception {
        event.getBot().sendRaw().rawLine(GeneralUtils.buildMessage(0, args.length, args));
    }
}

