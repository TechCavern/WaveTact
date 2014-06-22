package com.techcavern.wavetact.commands;

import com.techcavern.wavetact.utils.*;


public class BasicCommands {
    SimpleAction potato = new SimpleAction("potato", 0, "is a potato", true);
    SimpleMessage ping = new SimpleMessage("ping", 0, "pong", true);
    SimpleMessage pong = new SimpleMessage("pong", 0, "ping", true);
    SimpleMessage source = new SimpleMessage("source", 0,
            "http://github.com/TechCavern/WaveTact", true);

    public void basicCommands() {
        GeneralRegistry.Commands.add(potato);
        GeneralRegistry.Commands.add(ping);
        GeneralRegistry.Commands.add(pong);
        GeneralRegistry.Commands.add(source);
    }
}
