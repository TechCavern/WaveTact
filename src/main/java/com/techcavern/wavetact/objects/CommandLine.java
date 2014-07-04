package com.techcavern.wavetact.objects;

import com.techcavern.wavetact.utils.GeneralRegistry;

public abstract class CommandLine {

    private final String[] argument;
    private final String helpstring;
    private final boolean isPriority;

    protected CommandLine(String[] sa, String hs, boolean ds) {
        this.argument = sa;
        this.helpstring = hs;
        this.isPriority = ds;
        create();

    }

    public void create() {
        if (this.isPriority) {
            GeneralRegistry.CommandLineArguments.add(this);
        } else {
            GeneralRegistry.CommandLines.add(this);
        }
    }


    public String[] getArgument() {
        return this.argument;
    }

    public String getHelpString() {
        return this.helpstring;
    }

    public abstract void doAction(String[] args);

}





