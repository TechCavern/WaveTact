package com.techcavern.wavetact.commandline.utils;

import com.techcavern.wavetact.annot.CMDLine;
import com.techcavern.wavetact.utils.GeneralRegistry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.databaseUtils.SimpleActionUtils;
import com.techcavern.wavetact.utils.databaseUtils.SimpleMessageUtils;
import com.techcavern.wavetact.utils.objects.CommandLine;
import com.techcavern.wavetact.utils.objects.SimpleAction;
import com.techcavern.wavetact.utils.objects.SimpleMessage;


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
        SimpleMessage authors = new SimpleMessage("authors", 0, "Julian (JZTech101), Logan", true);
        SimpleMessage contributors = new SimpleMessage("contributors", 0, "Kenneth (kaendfinger), Sam (samrg472), Leah", true);
        SimpleAction nom = new SimpleAction("nom", 0, "noms on $*", true);
        GeneralRegistry.SimpleActions.add(potato);
        GeneralRegistry.SimpleMessages.add(ping);
        GeneralRegistry.SimpleMessages.add(pong);
        GeneralRegistry.SimpleMessages.add(source);
        GeneralRegistry.SimpleMessages.add(authors);
        GeneralRegistry.SimpleMessages.add(contributors);
        GeneralRegistry.SimpleActions.add(nom);
        SimpleActionUtils.saveSimpleActions();
        SimpleMessageUtils.saveSimpleMessages();
        System.out.println("Basic Commands Added");
        System.exit(0);

    }
}

