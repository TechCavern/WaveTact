package com.techcavern.wavetact.commandline.utils;

import com.techcavern.wavetact.annot.CMDLine;
import com.techcavern.wavetact.objects.CommandLine;
import com.techcavern.wavetact.objects.SimpleAction;
import com.techcavern.wavetact.objects.SimpleMessage;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.SaveUtils;


public class BasicCommands extends CommandLine {
    @CMDLine
    public BasicCommands() {
        super(GeneralUtils.toArray("addbasiccommands"), "No Arguments, Use it ONCE and ONLY ONCE to populate the Basic Commands", false);
    }

    @Override
    public void doAction(String[] args) {
        SimpleAction potato = new SimpleAction("potato", 0, "is a potato", true);
        SimpleMessage ping = new SimpleMessage("ping", 0, "pong", true);
        SimpleMessage pong = new SimpleMessage("pong", 0, "ping", true);
        SimpleMessage source = new SimpleMessage("source", 0,
                "http://github.com/TechCavern/WaveTact", true);
        GeneralRegistry.SimpleActions.add(potato);
        GeneralRegistry.SimpleMessages.add(ping);
        GeneralRegistry.SimpleMessages.add(pong);
        GeneralRegistry.SimpleMessages.add(source);
        SaveUtils.saveSimpleActions();
        SaveUtils.saveSimpleMessages();
        System.out.println("Basic Commands Added");
        System.exit(0);

    }
}

