package com.techcavern.wavetact.commands.controller;

import com.techcavern.wavetact.objects.Command;
import com.techcavern.wavetact.objects.SimpleAction;
import com.techcavern.wavetact.objects.SimpleMessage;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.IRCUtils;
import org.pircbotx.hooks.events.MessageEvent;


public class BasicCommands extends Command {
    public BasicCommands() {
        super("AddBasicCommands", 10);
    }

    @Override
    public void onCommand(MessageEvent<?> event, String... args)
            throws Exception {
        SimpleAction potato = new SimpleAction("potato", 0, "is a potato", true);
        SimpleMessage ping = new SimpleMessage("ping", 0, "pong", true);
        SimpleMessage pong = new SimpleMessage("pong", 0, "ping", true);
        SimpleMessage source = new SimpleMessage("source", 0,
                "http://github.com/TechCavern/WaveTact", true);
        GeneralRegistry.SimpleActions.add(potato);
        GeneralRegistry.SimpleMessages.add(ping);
        GeneralRegistry.SimpleMessages.add(pong);
        GeneralRegistry.SimpleMessages.add(source);
        IRCUtils.saveSimpleActions();
        IRCUtils.saveSimpleMessages();
        event.getChannel().send().message("Basic Commands Added");
    }    }

