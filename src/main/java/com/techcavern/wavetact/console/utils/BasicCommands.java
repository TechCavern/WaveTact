package com.techcavern.wavetact.console.utils;

import com.techcavern.wavetact.annot.CMDLine;
import com.techcavern.wavetact.utils.Registry;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.databaseUtils.SimpleActionUtils;
import com.techcavern.wavetact.utils.databaseUtils.SimpleMessageUtils;
import com.techcavern.wavetact.utils.objects.CommandLine;
import com.techcavern.wavetact.utils.objects.SimpleAction;
import com.techcavern.wavetact.utils.objects.SimpleMessage;

@CMDLine
public class BasicCommands extends CommandLine {

    public BasicCommands() {
        super(GeneralUtils.toArray("addbasiccommands"), "No Arguments, Use it ONCE and ONLY ONCE to populate the Basic Commands", false);
    }

    @Override
    public void doAction(String[] args) {
        SimpleAction potato = new SimpleAction("potato", 0, "is a potato", true);
        SimpleMessage ping = new SimpleMessage("ping", 0, "pong", true);
        SimpleMessage pong = new SimpleMessage("pong", 0, "ping", true);
        SimpleMessage releases = new SimpleMessage("releases", 0, "https://github.com/TechCavern/WaveTact/releases", true);
        SimpleMessage license = new SimpleMessage("license", 0, "MIT (Do What The Fuck You Want, We take ZERO liability)", true);
        SimpleMessage source = new SimpleMessage("source", 0,
                "http://github.com/TechCavern/WaveTact", true);
        SimpleMessage authors = new SimpleMessage("authors", 0, "Julian (JZTech101)", true);
        SimpleMessage contributors = new SimpleMessage("contributors", 0, "Kenneth (kaendfinger), Sam (samrg472), Logan, Laceh", true);
        SimpleAction nom = new SimpleAction("nom", 0, "noms on $*", true);
        Registry.SimpleActions.add(potato);
        Registry.SimpleMessages.add(ping);
        Registry.SimpleMessages.add(pong);
        Registry.SimpleMessages.add(source);
        Registry.SimpleMessages.add(license);
        Registry.SimpleMessages.add(releases);
        Registry.SimpleMessages.add(authors);
        Registry.SimpleMessages.add(contributors);
        Registry.SimpleActions.add(nom);
        SimpleActionUtils.saveSimpleActions();
        SimpleMessageUtils.saveSimpleMessages();
        System.out.println("Basic Commands Added");
        System.exit(0);

    }
}

